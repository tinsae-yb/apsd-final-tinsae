package org.example.crms.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

@Configuration
@ConfigurationProperties("jwt.signing")
@Data
public class JwtConfig {
    private String secret;
    private Long refreshTokenExpiration;
    private Long accessTokenExpiration;


    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
