package org.example.crms.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;


    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    private String password;
}
