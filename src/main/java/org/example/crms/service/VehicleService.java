package org.example.crms.service;

import org.example.crms.dto.vehicle.RegisterVehicleResponse;
import org.example.crms.dto.vehicle.VehicleBasicInformation;
import org.springframework.stereotype.Service;

@Service
public interface VehicleService {
    RegisterVehicleResponse registerVehicle(VehicleBasicInformation vehicleBasicInformation);
}
