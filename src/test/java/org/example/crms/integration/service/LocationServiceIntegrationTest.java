package org.example.crms.integration.service;


import org.example.crms.dto.location.LocationBasicInfo;
import org.example.crms.dto.location.LocationResponse;
import org.example.crms.repo.LocationRepository;
import org.example.crms.service.LocationService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class LocationServiceIntegrationTest {

   static PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

   @Autowired
   private LocationService locationService;

   @Autowired
    private LocationRepository locationRepository;



    @BeforeAll
    static void beforeAll() {
        postgres.start();

    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void beforeEach() {
        locationRepository.deleteAll();
    }

    @DynamicPropertySource
    static void setDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }


    @Test
    public void testAddLocation() {
        LocationBasicInfo locationBasicInfo = new LocationBasicInfo();
        locationBasicInfo.setCity("city");
        locationBasicInfo.setStreet("street");
        locationBasicInfo.setState("state");

        LocationResponse locationResponse = locationService.addLocation(locationBasicInfo);

        assertEquals(locationResponse.getCity(), locationBasicInfo.getCity());
        assertEquals(locationResponse.getStreet(), locationBasicInfo.getStreet());
        assertEquals(locationResponse.getState(), locationBasicInfo.getState());

    }

    @Test
    void getAllLocationsTest() {
        LocationBasicInfo locationBasicInfo = new LocationBasicInfo();
        locationBasicInfo.setCity("city");
        locationBasicInfo.setStreet("street");
        locationBasicInfo.setState("state");
        locationService.addLocation(locationBasicInfo);

        List<LocationResponse> locations =  locationService.getAllLocations();
        assertEquals(locations.size(), 1);

    }



}
