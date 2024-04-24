package org.example.crms.dto.user;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.crms.dto.RoleDTO;
import org.example.crms.entity.Role;
import org.example.crms.entity.User;

import java.util.List;

@Data
public class RegisterUserRequest {


    private String username;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password cannot be empty")
    private String password;


    @NotEmpty(message = "Roles cannot be empty")
    private List<RoleDTO> roles;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles.stream().map(RoleDTO::toRole).toList());
        return user;
    }
}
