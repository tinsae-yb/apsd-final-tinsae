package org.example.crms.repo;

import org.example.crms.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long>{
    Optional<Rental> findByReservationId(Long reservationId);
}
