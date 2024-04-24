package org.example.crms.dto.vehicle;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.crms.entity.Vehicle;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = false)
@Data
public class RegisterVehicleResponse  {
    private Long id;
    private VehicleBasicInformation vehicleInformation;

    public static  RegisterVehicleResponse fromVehicle(Vehicle vehicle) {
        RegisterVehicleResponse registerVehicleResponse = new RegisterVehicleResponse();
        registerVehicleResponse.setId(vehicle.getId());
        registerVehicleResponse.setVehicleInformation(VehicleBasicInformation.fromVehicle(vehicle));
        return registerVehicleResponse;
    }
}
