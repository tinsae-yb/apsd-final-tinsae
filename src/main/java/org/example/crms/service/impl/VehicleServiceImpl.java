package org.example.crms.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.crms.dto.vehicle.RegisterVehicleResponse;
import org.example.crms.dto.vehicle.VehicleBasicInformation;
import org.example.crms.entity.Vehicle;
import org.example.crms.exception.BadRequestException;
import org.example.crms.exception.NotFoundException;
import org.example.crms.repo.VehicleRepository;
import org.example.crms.service.VehicleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public RegisterVehicleResponse registerVehicle(VehicleBasicInformation vehicleBasicInformation) {

        try {
            Vehicle vehicle = vehicleBasicInformation.toVehicle();
            vehicle = vehicleRepository.save(vehicle);
            return RegisterVehicleResponse.fromVehicle(vehicle);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("vehicle_plate_key")) {
                throw new BadRequestException("Plate already exists");
            }
            throw new BadRequestException("Invalid data");
        }


    }

    @Override
    public RegisterVehicleResponse updateVehicle(Long id, VehicleBasicInformation vehicleBasicInformation) {

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehicle not found"));

        Vehicle newVehicle = vehicleBasicInformation.toVehicle();
        newVehicle.setId(vehicle.getId());
        newVehicle.setReservations(vehicle.getReservations());
        newVehicle = vehicleRepository.save(newVehicle);

        return RegisterVehicleResponse.fromVehicle(newVehicle);


    }


}
