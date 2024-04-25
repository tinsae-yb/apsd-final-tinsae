package org.example.crms.controllers;


import lombok.RequiredArgsConstructor;
import org.example.crms.dto.location.LocationBasicInfo;
import org.example.crms.dto.location.LocationResponse;
import org.example.crms.service.LocationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @PostMapping
    public LocationResponse addLocation(@RequestBody @Validated LocationBasicInfo locationBasicInfo){

        return locationService.addLocation(locationBasicInfo);
    }
}
