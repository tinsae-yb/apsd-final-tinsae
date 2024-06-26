package org.example.crms.repo;

import aj.org.objectweb.asm.commons.Remapper;
import org.example.crms.entity.Customer;
import org.example.crms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    Optional<Customer> findByEmail(String email);
}
