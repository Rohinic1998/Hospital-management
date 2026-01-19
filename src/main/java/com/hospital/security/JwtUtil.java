package com.hospital.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret:}")
    private String secret;

    @Value("${jwt.expiration:604800000}") // 7 days default
    private long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        if (secret == null || secret.isEmpty()) {
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            secret = Base64.getEncoder().encodeToString(key.getEncoded());
            System.out.println("Generated JWT Secret: " + secret);
        }
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    // Generate Token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    // Extract Email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Extract Role
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Validate Token
    public boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    // Check Expiration
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Parse Claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
