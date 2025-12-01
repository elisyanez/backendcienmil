package com.cienmilsabores.backendcienmil.service;

import java.util.Date;
import java.util.Objects;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.stereotype.Service;

import com.cienmilsabores.backendcienmil.model.Usuario;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    // Keep a secret long enough for HS256 (at least 32 bytes recommended)
    private final String SECRET = "miClaveSecretaSuperSeguraQueDebeSerLarga_y_mas_segura_123456";
    private final Key signingKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7 días

    public String generateToken(Usuario usuario) {
        Objects.requireNonNull(usuario, "usuario must not be null");

        return Jwts.builder()
            .setSubject(usuario.getCorreo())
            .claim("run", usuario.getRun())
            .claim("role", usuario.getRole())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String extractToken(HttpServletRequest request) {
        // 1. Intentar desde cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        // 2. Intentar desde header (fallback)
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
}