package org.example.crms.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Address pickupAddress;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Address dropoffAddress;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    private ReservationStatus status = ReservationStatus.PENDING;

    @JoinColumn(nullable = false)
    private LocalDate pickupDate;
    @JoinColumn(nullable = false)
    private LocalDate expectedDropOffDate;
    private LocalDate actualDropOffDate;

    private BigDecimal pricePerDay;



    public enum ReservationStatus {
        PENDING, CONFIRMED, PICKED,  CANCELLED
    }




}
