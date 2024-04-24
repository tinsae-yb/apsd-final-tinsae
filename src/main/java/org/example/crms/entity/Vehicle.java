package org.example.crms.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Vehicle {


    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(unique = true, nullable = false)
    private String plate;

    @Column(nullable = false)
    private String color;


    @Column(nullable = false)
    private BigDecimal pricePerDay;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private Integer mileage;

    @OneToMany(mappedBy = "vehicle")
    List<Reservation> reservations;

    public enum VehicleType {
        SEDAN, SUV, TRUCK, VAN, MOTORCYCLE
    }
}



