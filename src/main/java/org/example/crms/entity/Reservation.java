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
    private Location pickupLocation;

    @ManyToOne
    private Location dropOffLocation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    @JoinColumn(nullable = false)
    private LocalDate pickupDate;
    @JoinColumn(nullable = false)
    private LocalDate expectedDropOffDate;
    private LocalDate actualDropOffDate;

    private BigDecimal pricePerDay;

    @OneToOne(mappedBy = "reservation")
    private Rental rental;

    public ReservationStatus getStatus() {
        if(rental != null) {
            return ReservationStatus.PAID;
        }
        return status;
    }




    public enum ReservationStatus {
        PENDING, CONFIRMED, PICKED,  CANCELLED, COMPLETED, PAID
    }




}
