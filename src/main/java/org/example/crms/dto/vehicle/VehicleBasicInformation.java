package org.example.crms.dto.vehicle;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.crms.entity.Vehicle;

import java.math.BigDecimal;

@Data
public class VehicleBasicInformation {

    @NotNull
    private String make;
    @NotNull
    private String model;
    @NotNull
    private String plate;
    @NotNull
    private int year;

    @NotNull
    private String color;

    @NotNull
    private BigDecimal pricePerDay;

    @NotNull
    private Vehicle.VehicleType vehicleType;


    private Vehicle.VehicleAvailability availability;

    private Integer mileage;

    @NotNull
    @Min(1)
    private Long locationId;

    public Vehicle toVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setPlate(plate);
        vehicle.setYear(year);
        vehicle.setColor(color);
        vehicle.setPricePerDay(pricePerDay);
        vehicle.setVehicleType(vehicleType);
        vehicle.setMileage(mileage);
        vehicle.setAvailability(availability);
        return vehicle;
    }

    public static VehicleBasicInformation fromVehicle(Vehicle vehicle) {
        VehicleBasicInformation vehicleBasicInformation = new VehicleBasicInformation();
        vehicleBasicInformation.setMake(vehicle.getMake());
        vehicleBasicInformation.setModel(vehicle.getModel());
        vehicleBasicInformation.setPlate(vehicle.getPlate());
        vehicleBasicInformation.setYear(vehicle.getYear());
        vehicleBasicInformation.setColor(vehicle.getColor());
        vehicleBasicInformation.setPricePerDay(vehicle.getPricePerDay());
        vehicleBasicInformation.setVehicleType(vehicle.getVehicleType());
        vehicleBasicInformation.setMileage(vehicle.getMileage());
        vehicleBasicInformation.setLocationId(vehicle.getLocation().getId());
        vehicleBasicInformation.setAvailability(vehicle.getAvailability());
        return vehicleBasicInformation;
    }
}
