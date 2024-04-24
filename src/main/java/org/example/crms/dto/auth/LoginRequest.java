package org.example.crms.dto.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;
}
