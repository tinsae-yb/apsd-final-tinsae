package org.example.crms.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Users")
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
    @ManyToMany
    private List<Role> roles;
}
