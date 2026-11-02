package com.giannis.workout_tracker.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }// we turn the secret value into a key

    public String generateToken(String username) {
        return Jwts.builder() // its a string that says: this key is "username" and expires in 24 hours
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)  // and it signs the key
                .compact();
    }

    /**
     * if the token is invalid or expired it throws a JwException
     *
     * @return the username of the user with the  token given as a parameter
     */
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}