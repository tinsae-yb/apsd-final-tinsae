package org.example.crms.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.crms.config.JwtConfig;
import org.example.crms.exception.UnAuthorizedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component()
@RequiredArgsConstructor
@Slf4j
public class JWTUtil {

    private final JwtConfig jwtConfig;




    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token) {return Jwts.parser().verifyWith(jwtConfig.getSecretKey()).build().parseSignedClaims(token).getPayload();}

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Date extractExpiration(String token) { return extractClaim(token, Claims::getExpiration); }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(UserDetails userDetails ) {


        String email = userDetails.getUsername();
       List<String> authorities =  userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();


     return   Jwts.builder()
               .subject(email)
               .claim("role",authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(jwtConfig.getAccessTokenExpiration(), ChronoUnit.MILLIS)))
               .signWith(jwtConfig.getSecretKey())
                .compact();
    }


    public String generateRefreshToken(UserDetails userDetails) {
        String email = userDetails.getUsername();
        return   Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(jwtConfig.getAccessTokenExpiration(), ChronoUnit.MILLIS)))
                .signWith(jwtConfig.getSecretKey())
                .compact();

         }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(jwtConfig.getSecretKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            throw new UnAuthorizedException("Invalid JWT token");
        }

    }

    public String getToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {return bearerToken.substring(7); }
        return null;
    }
}
