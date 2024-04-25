package org.example.crms.repo;

import org.example.crms.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>{
    @Query("SELECT v FROM Vehicle v " +
            "WHERE (:location IS NULL OR v.location.id = :location) " +
            "AND (:type IS NULL OR v.vehicleType = :type) " +
            "AND (:available IS NULL OR v.availability = :available)"
    )
    List<Vehicle> findByLocationIdAndVehicleType(@Param("location") Long location, @Param("type") Vehicle.VehicleType type, @Param("available") Vehicle.VehicleAvailability available);
}
