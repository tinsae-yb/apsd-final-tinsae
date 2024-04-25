package org.example.crms.service;


import org.example.crms.dto.location.LocationBasicInfo;
import org.example.crms.dto.location.LocationResponse;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {
    LocationResponse addLocation(LocationBasicInfo locationBasicInfo);
}
