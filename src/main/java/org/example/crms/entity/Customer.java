package org.example.crms.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer extends User{

    @OneToMany(mappedBy = "customer")
   private List<Reservation> reservations;
}
