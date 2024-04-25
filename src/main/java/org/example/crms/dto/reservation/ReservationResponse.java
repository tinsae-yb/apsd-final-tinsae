package org.example.crms.dto.reservation;

import lombok.Data;
import org.example.crms.dto.location.LocationResponse;
import org.example.crms.dto.user.BasicCustomerInformation;
import org.example.crms.dto.user.RegisterUserResponse;
import org.example.crms.dto.vehicle.VehicleResponse;
import org.example.crms.entity.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservationResponse {


    private Long id;


    private LocationResponse pickupAddress;

    private LocationResponse dropOffAddress;


    private VehicleResponse vehicle;


    private BasicCustomerInformation customer;

    private Reservation.ReservationStatus status = Reservation.ReservationStatus.PENDING;

    private LocalDate pickupDate;

    private LocalDate expectedDropOffDate;

    private LocalDate actualDropOffDate;

    private BigDecimal pricePerDay;

    private BigDecimal totalPrice;

    public static ReservationResponse fromReservation(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setPickupAddress(LocationResponse.fromLocation(reservation.getPickupLocation()));
        if (reservation.getDropOffLocation() != null) {
            reservationResponse.setDropOffAddress(LocationResponse.fromLocation(reservation.getDropOffLocation()));
        }
        if (reservation.getActualDropOffDate() != null) {
            reservationResponse.setActualDropOffDate(reservation.getActualDropOffDate());
        }
        reservationResponse.setVehicle(VehicleResponse.fromVehicle(reservation.getVehicle()));
        reservationResponse.setCustomer(BasicCustomerInformation.fromCustomer(reservation.getCustomer()));
        reservationResponse.setStatus(reservation.getStatus());
        reservationResponse.setPickupDate(reservation.getPickupDate());
        reservationResponse.setExpectedDropOffDate(reservation.getExpectedDropOffDate());
        reservationResponse.setActualDropOffDate(reservation.getActualDropOffDate());
        reservationResponse.setPricePerDay(reservation.getPricePerDay());

        // calculate total price
        long days = reservation.getExpectedDropOffDate().toEpochDay() - reservation.getPickupDate().toEpochDay();
        reservationResponse.setTotalPrice(reservation.getPricePerDay().multiply(BigDecimal.valueOf(days)));

        return reservationResponse;
    }
}
