package org.example.crms.service;

import org.example.crms.dto.auth.LoginRequest;
import org.example.crms.dto.auth.LoginResponse;
import org.example.crms.dto.auth.RefreshTokenRequest;
import org.example.crms.dto.auth.RefreshTokenResponse;
import org.example.crms.dto.user.RegisterUserRequest;
import org.example.crms.dto.user.RegisterUserResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    LoginResponse login(LoginRequest loginRequest);

    RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest);
}
