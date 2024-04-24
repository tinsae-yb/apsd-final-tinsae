package org.example.crms.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;


   public enum RoleType {
        ADMIN, CUSTOMER, AGENT
    }
}


