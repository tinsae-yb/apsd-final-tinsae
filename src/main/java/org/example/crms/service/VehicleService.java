package org.example.crms.service;

import org.example.crms.dto.vehicle.VehicleResponse;
import org.example.crms.dto.vehicle.VehicleBasicInformation;
import org.example.crms.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehicleService {
    VehicleResponse registerVehicle(VehicleBasicInformation vehicleBasicInformation);

    VehicleResponse updateVehicle(Long id, VehicleBasicInformation vehicleBasicInformation);

    List<VehicleResponse> getVehicles(Long location, Vehicle.VehicleType type, Boolean available);


}
