package org.example.crms.controllers;


import lombok.RequiredArgsConstructor;
import org.example.crms.dto.vehicle.RegisterVehicleResponse;
import org.example.crms.dto.vehicle.VehicleBasicInformation;
import org.example.crms.service.VehicleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    final private VehicleService vehicleService;

    @PostMapping()
    public RegisterVehicleResponse registerVehicle(@Validated @RequestBody VehicleBasicInformation vehicleBasicInformation){
        return vehicleService.registerVehicle(vehicleBasicInformation);
    }
}
