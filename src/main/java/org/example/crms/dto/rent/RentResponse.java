package org.example.crms.dto.rent;

import lombok.Data;
import org.example.crms.dto.reservation.ReservationResponse;
import org.example.crms.entity.Rental;

@Data
public class RentResponse {

    ReservationResponse reservation;

    public static RentResponse fromRental(Rental rental) {
        RentResponse rentResponse = new RentResponse();
        rentResponse.setReservation(ReservationResponse.fromReservation(rental.getReservation()));
        return rentResponse;
    }
}
