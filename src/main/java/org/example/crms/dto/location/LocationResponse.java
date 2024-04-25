package org.example.crms.dto.location;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.crms.entity.Address;
import org.example.crms.entity.Location;

@EqualsAndHashCode(callSuper = false)
@Data
public class LocationResponse extends LocationBasicInfo{
    private  Long id;

    public static LocationResponse fromAddress(Location location) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setId(location.getId());
        locationResponse.setStreet(location.getStreet());
        locationResponse.setCity(location.getCity());
        locationResponse.setState(location.getState());
        return locationResponse;
    }
}
