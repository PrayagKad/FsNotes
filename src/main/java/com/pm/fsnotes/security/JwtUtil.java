package com.pm.fsnotes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Secret key for signing JWT (keep it safe)
    private String secret = "mysecretkey123";

    // Token validity: 10 hours
    private long jwtExpiration = 10 * 60 * 60 * 1000;

    // Generate JWT token using username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // store username in token
                .setIssuedAt(new Date()) // token issue time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // expiry
                .signWith(SignatureAlgorithm.HS256, secret) // sign with secret key
                .compact();
    }

    // Validate token (check username & expiry)
    public boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    // Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Check if token expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
