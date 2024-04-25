package org.example.crms.dto.location;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.crms.entity.Address;
import org.example.crms.entity.Location;

@Data
public class LocationBasicInfo {
    @NotBlank
    private  String street;
    @NotBlank
    private  String city;
    @NotBlank
    private  String state;

    public Location toLocation() {
        Location location = new Location();
        location.setStreet(street);
        location.setCity(city);
        location.setState(state);
        return location;
    }
}
