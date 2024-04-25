package org.example.crms.service;


import org.example.crms.dto.payment.PaymentRequest;
import org.example.crms.dto.rent.RentResponse;
import org.example.crms.dto.reservation.ReservationRequest;
import org.example.crms.dto.reservation.ReservationResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservationRequest, UserDetails userDetails);

    ReservationResponse confirmReservation(Long id, UserDetails userDetails);

    ReservationResponse cancelReservation(Long id, UserDetails userDetails);

    List<ReservationResponse> getReservations(Long customerId, UserDetails userDetails);

    RentResponse makePayment(PaymentRequest paymentRequest, UserDetails userDetails, Long reservationId);

    ReservationResponse pickCar(Long id, UserDetails userDetails);


    ReservationResponse returnVehicle(Long id, UserDetails userDetails, Long locationId);
}
