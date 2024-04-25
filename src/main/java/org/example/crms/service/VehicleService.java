package org.example.crms.service;

import org.example.crms.dto.vehicle.VehicleResponse;
import org.example.crms.dto.vehicle.VehicleBasicInformation;
import org.springframework.stereotype.Service;

@Service
public interface VehicleService {
    VehicleResponse registerVehicle(VehicleBasicInformation vehicleBasicInformation);

    VehicleResponse updateVehicle(Long id, VehicleBasicInformation vehicleBasicInformation);
}
