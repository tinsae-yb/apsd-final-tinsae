package org.example.crms.dto.auth;


public record LoginResponse(String accessToken, String refreshToken) {
}
