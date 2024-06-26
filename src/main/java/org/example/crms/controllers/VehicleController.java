package org.example.crms.controllers;


import lombok.RequiredArgsConstructor;
import org.example.crms.dto.vehicle.VehicleResponse;
import org.example.crms.dto.vehicle.VehicleBasicInformation;
import org.example.crms.entity.Vehicle;
import org.example.crms.service.VehicleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    final private VehicleService vehicleService;

    @PostMapping()
    public VehicleResponse registerVehicle(@Validated @RequestBody VehicleBasicInformation vehicleBasicInformation){
        return vehicleService.registerVehicle(vehicleBasicInformation);
    }

    @PutMapping("/{id}")
    public VehicleResponse updateVehicle(@PathVariable Long id, @RequestBody VehicleBasicInformation vehicleBasicInformation){
        return vehicleService.updateVehicle(id, vehicleBasicInformation);
    }


    @GetMapping()
    public List<VehicleResponse> getVehicle(
            @RequestParam(required = false) Long location,
            @RequestParam(required = false) Vehicle.VehicleType type,
            @RequestParam(required = false) Boolean available
    ){
        return vehicleService.getVehicles(location, type, available);
    }


}
