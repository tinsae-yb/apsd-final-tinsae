package org.example.crms.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Users")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
 public class User {

    @Id
    @GeneratedValue
    private Long id;


    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
