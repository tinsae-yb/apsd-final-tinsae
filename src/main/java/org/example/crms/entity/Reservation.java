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
    @Column(nullable = false)
    private Address pickupAddress;

    @ManyToOne
    @Column(nullable = false)
    private Address dropoffAddress;

    @ManyToOne
    @Column(nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @Column(nullable = false)
    private Customer customer;

    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(nullable = false)
    private LocalDate pickupDate;
    @Column(nullable = false)
    private LocalDate expectedDropOffDate;
    private LocalDate actualDropOffDate;

    private BigDecimal pricePerDay;



    public enum ReservationStatus {
        PENDING, CONFIRMED, PICKED,  CANCELLED
    }




}
