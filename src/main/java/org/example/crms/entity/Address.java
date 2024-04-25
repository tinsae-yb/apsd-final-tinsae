package org.example.crms.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String city;
    private String state;
}
