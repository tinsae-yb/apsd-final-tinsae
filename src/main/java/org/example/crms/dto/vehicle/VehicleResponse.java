package org.example.crms.dto.vehicle;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.crms.dto.location.LocationResponse;
import org.example.crms.entity.Vehicle;


@EqualsAndHashCode(callSuper = false)
@Data
public class VehicleResponse {
    private Long id;
    private LocationResponse location;
    private VehicleBasicInformation vehicleInformation;

    public static VehicleResponse fromVehicle(Vehicle vehicle) {
        VehicleResponse registerVehicleResponse = new VehicleResponse();
        registerVehicleResponse.setId(vehicle.getId());
        registerVehicleResponse.setVehicleInformation(VehicleBasicInformation.fromVehicle(vehicle));
        registerVehicleResponse.setLocation(LocationResponse.fromLocation(vehicle.getLocation()));
        return registerVehicleResponse;
    }
}
