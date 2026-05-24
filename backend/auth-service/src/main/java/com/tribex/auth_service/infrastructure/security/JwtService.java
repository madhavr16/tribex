package com.tribex.auth_service.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import java.util.Date;

/*
    Handles JWT token generation & validation
 */
@Service
public class JwtService {

    /*
     * Secret key used for signing JWT
     * 
     * IMPORTANT:
     * Must be at least 32 characters long
     * for HS256 algorithm
     */
    @Value("${JWT_SECRET}")
    private String secret;

    /*
     * Convert secret string into Key object
     */
    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    /*
     * Generate JWT token
     */
    public String generateToken(String email) {

        return Jwts.builder()

                /*
                 * Store email inside token
                 */
                .setSubject(email)

                /*
                 * Token creation time
                 */
                .setIssuedAt(new Date())

                /*
                 * Token expiry
                 * 24 hours
                 */
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60 * 24))

                /*
                 * Sign token securely
                 */
                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256)

                .compact();
    }

    /*
     * Extract email from token
     */
    public String extractEmail(String token) {

        Claims claims = Jwts.parserBuilder()

                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();

        return claims.getSubject();
    }
}