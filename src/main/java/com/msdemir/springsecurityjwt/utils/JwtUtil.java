package com.msdemir.springsecurityjwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtil {

    @Value("${app.jwt-secret}")
    String JWT_SECRET;

    @Value("${app.jwt-expiration}")
    Long JWT_EXPIRATION;

    public String generateToken(String username, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .addClaims(extraClaims)
                .setSubject(username)
                .signWith(getSignKey())
                .compact();
    }

    public boolean validateToken(String token) {
        return getClaimsFromToken(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    public String extractUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
