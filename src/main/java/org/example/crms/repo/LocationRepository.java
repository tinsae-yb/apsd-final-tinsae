package org.example.crms.repo;

import org.example.crms.entity.Address;
import org.example.crms.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long>{
}
