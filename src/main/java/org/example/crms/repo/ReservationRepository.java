package org.example.crms.repo;

import org.example.crms.entity.Customer;
import org.example.crms.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

   @Query("SELECT r FROM Reservation r WHERE :customerId IS NULL OR  r.customer.id = :customerId")
    List<Reservation>  findAllByCustomerId(Long customerId);
}
