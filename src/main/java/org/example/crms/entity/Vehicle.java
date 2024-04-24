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

    private String make;
    private String model;
    private String year;
    private String color;
    private BigDecimal pricePerDay;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private Integer mileage;

    @OneToMany(mappedBy = "vehicle")
    List<Reservation> reservations;


    public enum VehicleType {
        SEDAN, SUV, TRUCK, VAN, MOTORCYCLE
    }
}



