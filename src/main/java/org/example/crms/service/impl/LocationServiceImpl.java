package org.example.crms.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.crms.dto.location.LocationBasicInfo;
import org.example.crms.dto.location.LocationResponse;
import org.example.crms.entity.Location;
import org.example.crms.repo.LocationRepository;
import org.example.crms.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    @Override
    public LocationResponse addLocation(LocationBasicInfo locationBasicInfo) {
        Location location = locationBasicInfo.toLocation();
        location = locationRepository.save(location);;
        return LocationResponse.fromLocation(location);

    }

    @Override
    public List<LocationResponse> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(LocationResponse::fromLocation).toList();
    }
}
