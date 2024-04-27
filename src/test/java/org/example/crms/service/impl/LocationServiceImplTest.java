package org.example.crms.service.impl;

import org.example.crms.dto.location.LocationBasicInfo;
import org.example.crms.dto.location.LocationResponse;
import org.example.crms.entity.Location;
import org.example.crms.repo.LocationRepository;
import org.example.crms.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class LocationServiceImplTest {

    @MockBean
    private  LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;





    @Test
    void addLocation() {
        LocationBasicInfo locationBasicInfo = new LocationBasicInfo();
        locationBasicInfo.setStreet("street");
        locationBasicInfo.setCity("city");
        locationBasicInfo.setState("state");
        Location location = new Location();
        location.setStreet("street");
        location.setCity("city");
        location.setState("state");
        when(locationRepository.save(location)).thenReturn(location);
        LocationResponse locationResponse = locationService.addLocation(locationBasicInfo);
        assertEquals(locationResponse.getStreet(), locationBasicInfo.getStreet());
        assertEquals(locationResponse.getCity(), locationBasicInfo.getCity());
        assertEquals(locationResponse.getState(), locationBasicInfo.getState());
    }


    @Test
    void getAllLocations() {
        Location location = new Location();
        location.setStreet("street");
        location.setCity("city");
        location.setState("state");
        when(locationRepository.findAll()).thenReturn(List.of(location));
        assertEquals(locationService.getAllLocations().size(), 1);
    }



}