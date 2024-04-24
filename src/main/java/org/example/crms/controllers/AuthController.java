package org.example.crms.controllers;

import lombok.RequiredArgsConstructor;
import org.example.crms.dto.auth.LoginRequest;
import org.example.crms.dto.auth.LoginResponse;
import org.example.crms.dto.auth.RefreshTokenRequest;
import org.example.crms.dto.auth.RefreshTokenResponse;
import org.example.crms.dto.user.RegisterUserRequest;
import org.example.crms.dto.user.RegisterUserResponse;
import org.example.crms.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


    @PostMapping("/login")
    public LoginResponse login(@Validated @RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public RegisterUserResponse register(@RequestBody @Validated RegisterUserRequest registerUserRequest) {
        return authService.register(registerUserRequest);
    }



    @PostMapping("/refresh")
    public RefreshTokenResponse refresh( @RequestBody @Validated  RefreshTokenRequest refreshTokenRequest) {
        System.out.println("refreshTokenRequest = " + refreshTokenRequest);
        return authService.refresh(refreshTokenRequest);

    }
}
