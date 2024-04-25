package org.example.crms.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ReservationRequest {


    @NotNull
    private Long vehicleId;

    @NotNull
    private LocalDate pickupDate;

    @NotNull
    private LocalDate dropOffDate;

    private Long customerId;

}
