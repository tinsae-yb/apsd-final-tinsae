package org.example.crms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.crms.dto.payment.PaymentRequest;
import org.example.crms.dto.rent.RentResponse;
import org.example.crms.dto.reservation.ReservationRequest;
import org.example.crms.dto.reservation.ReservationResponse;
import org.example.crms.entity.*;
import org.example.crms.exception.BadRequestException;
import org.example.crms.exception.NotFoundException;
import org.example.crms.repo.*;
import org.example.crms.service.ReservationService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final RentalRepository rentalRepository;
    private final PaymentRepository paymentRepository;
    private final LocationRepository locationRepository;



    @Override
    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservationRequest, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
        Customer customer;


        if (!isCustomer(user)) {


            if (reservationRequest.getCustomerId() == null) {
                throw new NotFoundException("Customer id is required");
            }
            customer = customerRepository.findById(reservationRequest.getCustomerId()).orElseThrow(() -> new NotFoundException("Customer not found"));
        } else {
             customerRepository.findAll().forEach(System.out::println);
            customer = customerRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("Customer not found"));

            System.out.println(customer);
        }

        Vehicle vehicle = vehicleRepository.findById(reservationRequest.getVehicleId()).orElseThrow(() -> new NotFoundException("Vehicle not found"));

        if (vehicle.getAvailability() == Vehicle.VehicleAvailability.UNAVAILABLE) {
            throw new BadRequestException("Vehicle is not available");
        }

        // check pickup date is after today
        if (reservationRequest.getPickupDate().isBefore(java.time.LocalDate.now())) {
            throw new BadRequestException("Pickup date should be after today");
        }

        // check return date is after pickup date
        if (reservationRequest.getDropOffDate().isBefore(reservationRequest.getPickupDate())) {
            throw new BadRequestException("Return date should be after pickup date");
        }

        Reservation reservation = new Reservation();
        reservation.setPickupLocation(vehicle.getLocation());
        reservation.setPickupDate(reservationRequest.getPickupDate());
        reservation.setExpectedDropOffDate(reservationRequest.getDropOffDate());
        reservation.setCustomer(customer);
        reservation.setVehicle(vehicle);
        reservation.setPricePerDay(vehicle.getPricePerDay());

        vehicle.setAvailability(Vehicle.VehicleAvailability.UNAVAILABLE);
        vehicleRepository.save(vehicle);
        reservationRepository.save(reservation);

        return ReservationResponse.fromReservation(reservation);


    }

    @Override
    @Transactional
    public ReservationResponse confirmReservation(Long id, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found"));


        validateConfirmationOrCancellation(reservation, false);

        if (isCustomer(user)) {
            // check if the reservation belongs to the customer
            if (!reservation.getCustomer().getId().equals(user.getId())) {
                throw new BadRequestException("You are not allowed to confirm this reservation");
            }


        }
        reservation.setStatus(Reservation.ReservationStatus.CONFIRMED);

        reservationRepository.save(reservation);
        return ReservationResponse.fromReservation(reservation);


    }

    @Override
    public ReservationResponse cancelReservation(Long id, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found"));
        validateConfirmationOrCancellation(reservation, true);

        if (isCustomer(user)) {
            // check if the reservation belongs to the customer
            if (!reservation.getCustomer().getId().equals(user.getId())) {
                throw new BadRequestException("You are not allowed to cancel this reservation");
            }

        }
        reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
        reservation.getVehicle().setAvailability(Vehicle.VehicleAvailability.AVAILABLE);
        vehicleRepository.save(reservation.getVehicle());

        reservationRepository.save(reservation);
        return ReservationResponse.fromReservation(reservation);
    }

    @Override
    public List<ReservationResponse> getReservations(Long customerId, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));

        if (isCustomer(user)) {
            customerId = user.getId();
        }

        if (customerId != null) {
            customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer not found"));
        }


        return reservationRepository.findAllByCustomerId(customerId).stream().map(ReservationResponse::fromReservation).toList();


    }

    @Override
    @Transactional
    public RentResponse makePayment(PaymentRequest paymentRequest, UserDetails userDetails, Long reservationId) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("Reservation not found"));
        rentalRepository.findByReservationId(reservationId).ifPresent(rental -> {
            throw new BadRequestException("Payment is already made for this reservation");
        });
        if (isCustomer(user)) {
            // check if the reservation belongs to the customer
            if (!reservation.getCustomer().getId().equals(user.getId())) {
                throw new BadRequestException("You are not allowed to make payment for this reservation");
            }
        }

        if (reservation.getStatus().equals(Reservation.ReservationStatus.PENDING)) {
            throw new BadRequestException("Reservation is not confirmed yet");
        }
        if (reservation.getStatus().equals(Reservation.ReservationStatus.COMPLETED)) {
            throw new BadRequestException("Reservation is already completed");
        }

        if (reservation.getStatus().equals(Reservation.ReservationStatus.CANCELLED)) {
            throw new BadRequestException("Reservation is already cancelled");
        }

        if (reservation.getStatus().equals(Reservation.ReservationStatus.PICKED)) {
            throw new BadRequestException("Vehicle is already picked up");
        }

        Payment payment = new Payment();

       // calculate total price
        long days = reservation.getPickupDate().until(reservation.getExpectedDropOffDate()).getDays();
        payment.setPaymentAmount(reservation.getPricePerDay().multiply(java.math.BigDecimal.valueOf(days)));

        Rental rental = new Rental();
        rental.setReservation(reservation);
        rental.setPayment(payment);
        rentalRepository.save(rental);
        paymentRepository.save(payment);

        reservation.setStatus(Reservation.ReservationStatus.PAID);
        reservationRepository.save(reservation);
        return RentResponse.fromRental(rental);
    }

    @Override
    @Transactional
    public ReservationResponse pickCar(Long id, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException("User not found"));
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (isCustomer(user)) {
            if (!reservation.getCustomer().getId().equals(user.getId())) {
                throw new BadRequestException("You are not allowed to pick up this reservation");
            }
        }

        if (!reservation.getStatus().equals(Reservation.ReservationStatus.PAID)) {
            throw new BadRequestException("Reservation is not paid yet");
        }


        reservation.setStatus(Reservation.ReservationStatus.PICKED);
       reservation =  reservationRepository.save(reservation);
        return ReservationResponse.fromReservation(reservation);
    }

    @Transactional
    @Override
    public ReservationResponse returnVehicle(Long id, UserDetails userDetails, Long locationId) {
          Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservation not found"));
          Location location = locationRepository.findById(locationId).orElseThrow(() -> new NotFoundException("Location not found"));
          Vehicle vehicle = vehicleRepository.findById(reservation.getVehicle().getId()).orElseThrow(() -> new NotFoundException("Vehicle not found"));

        if (!reservation.getStatus().equals(Reservation.ReservationStatus.PICKED)) {
            throw new BadRequestException("Vehicle is not picked up yet");
        }

        vehicle.setLocation(location);
        vehicle.setAvailability(Vehicle.VehicleAvailability.AVAILABLE);
        vehicleRepository.save(vehicle);
        reservation.setStatus(Reservation.ReservationStatus.COMPLETED);
         reservation =   reservationRepository.save(reservation);

        return ReservationResponse.fromReservation(reservation);

    }


    public void validateConfirmationOrCancellation(Reservation reservation, boolean cancellation) {
        if (reservation.getStatus().equals(Reservation.ReservationStatus.CANCELLED)) {
            throw new BadRequestException("Reservation is already cancelled");
        }
        if (reservation.getStatus().equals(Reservation.ReservationStatus.CONFIRMED) && !cancellation) {
            throw new BadRequestException("Reservation is already confirmed");
        }

        if (reservation.getStatus().equals(Reservation.ReservationStatus.PICKED)) {
            throw new BadRequestException("Vehicle is already picked up");
        }

        if (reservation.getStatus().equals(Reservation.ReservationStatus.COMPLETED)) {
            throw new BadRequestException("Reservation is already completed");
        }
        if (reservation.getStatus().equals(Reservation.ReservationStatus.PAID)) {
            throw new BadRequestException("Payment is already made for this reservation");
        }
    }


    public boolean isCustomer(User user) {
        return user.getRoles().stream().noneMatch(role -> role.getRoleType().equals(Role.RoleType.ADMIN) || role.getRoleType().equals(Role.RoleType.AGENT));
    }
}
