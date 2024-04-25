package org.example.crms.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.crms.dto.vehicle.VehicleResponse;
import org.example.crms.dto.vehicle.VehicleBasicInformation;
import org.example.crms.entity.Vehicle;
import org.example.crms.exception.BadRequestException;
import org.example.crms.exception.NotFoundException;
import org.example.crms.repo.LocationRepository;
import org.example.crms.repo.VehicleRepository;
import org.example.crms.service.VehicleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final LocationRepository locationRepository;

    @Override
    public VehicleResponse registerVehicle(VehicleBasicInformation vehicleBasicInformation) {

        try {
            Vehicle vehicle = vehicleBasicInformation.toVehicle();
            vehicle.setLocation(locationRepository.findById(vehicleBasicInformation.getLocationId()).orElseThrow(() -> new NotFoundException("Location not found")));
            vehicle = vehicleRepository.save(vehicle);
            return VehicleResponse.fromVehicle(vehicle);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("vehicle_plate_key")) {
                throw new BadRequestException("Plate already exists");
            }
            throw new BadRequestException("Invalid data");
        }


    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleBasicInformation vehicleBasicInformation) {

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle not found"));

        Vehicle newVehicle = vehicleBasicInformation.toVehicle();
        vehicle.setLocation(locationRepository.findById(vehicleBasicInformation.getLocationId()).orElseThrow(() -> new NotFoundException("Location not found")));
        newVehicle.setId(vehicle.getId());
        newVehicle.setReservations(vehicle.getReservations());
        newVehicle = vehicleRepository.save(newVehicle);

        return VehicleResponse.fromVehicle(newVehicle);


    }

    @Override
    public List<VehicleResponse> getVehicles(Long location, Vehicle.VehicleType type, Boolean available) {
        List<Vehicle> vehicles ;
        if(available == null) {

         vehicles=   vehicleRepository.findByLocationIdAndVehicleType(location, type, null);
        }else if(available) {
            vehicles = vehicleRepository.findByLocationIdAndVehicleType(location, type, Vehicle.VehicleAvailability.AVAILABLE);

        }else {
            vehicles = vehicleRepository.findByLocationIdAndVehicleType(location, type, Vehicle.VehicleAvailability.UNAVAILABLE);
        }
        return vehicles.stream().map(VehicleResponse::fromVehicle).toList();

    }


}
