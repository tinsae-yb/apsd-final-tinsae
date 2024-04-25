package org.example.crms.controllers;


import lombok.RequiredArgsConstructor;
import org.example.crms.dto.payment.PaymentRequest;
import org.example.crms.dto.rent.RentResponse;
import org.example.crms.dto.reservation.ReservationRequest;
import org.example.crms.dto.reservation.ReservationResponse;
import org.example.crms.service.ReservationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;





    @PostMapping
    public ReservationResponse createReservation(@Validated @RequestBody ReservationRequest reservationRequest, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return reservationService.createReservation(reservationRequest, userDetails);
    }

    @PutMapping(value = "/{id}", params = "cancel")
    public ReservationResponse cancelReservation(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return reservationService.cancelReservation(id, userDetails);
    }
  @PutMapping(value = "/{id}", params = "confirm")
    public ReservationResponse confirmReservation(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return reservationService.confirmReservation(id, userDetails);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'AGENT')")
    @PutMapping(value = "/{id}", params = "pick")
    public ReservationResponse pickCar(@PathVariable Long id, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return reservationService.pickCar(id, userDetails);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'AGENT')")
    @PutMapping(value = "/{id}", params = "return")
    public ReservationResponse returnVehicle(@PathVariable Long id, @RequestParam(value = "location_id", required = true) Long locationId,   Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return reservationService.returnVehicle(id, userDetails, locationId);
    }

    @GetMapping()
    public List<ReservationResponse> getReservation(@RequestParam(required = false) Long customerId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return reservationService.getReservations(customerId, userDetails);
    }

    @PostMapping("/{id}/make-payment")
    public RentResponse makePayment(@Validated @RequestBody PaymentRequest paymentRequest, @PathVariable("id") Long reservationId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return reservationService.makePayment(paymentRequest, userDetails,  reservationId);
    }



}
