package org.example.crms.repo;

import org.example.crms.entity.Customer;
import org.example.crms.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation>  findAllByCustomerId(Long customerId);
}
