package com.cienmilsabores.backendcienmil.filter;

import com.cienmilsabores.backendcienmil.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("JWT filter -> method={} uri={} origin={} authHeader={}",
                request.getMethod(),
                request.getRequestURI(),
                request.getHeader("Origin"),
                request.getHeader("Authorization") != null ? "present" : "absent");

        try {
            String token = jwtService.extractToken(request);
            log.info("Token extracted: {}", (token != null ? "yes (length: " + token.length() + ")" : "no"));

            if (token != null && jwtService.validateToken(token)) {
                log.info("Token is valid");

                Claims claims = extractAllClaims(token);
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                log.info("Token claims -> username={} role={}", username, role);

                if (username != null) {
                    List<GrantedAuthority> authorities = new ArrayList<>();

                    if (role != null && !role.trim().isEmpty()) {
                        String authority = role.trim().toUpperCase();
                        if (!authority.startsWith("ROLE_")) {
                            authority = "ROLE_" + authority;
                        }
                        authorities.add(new SimpleGrantedAuthority(authority));
                        log.info("Authority created: {}", authority);
                    } else {
                        log.warn("No role found in token");
                    }

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    authorities
                            );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.info("Authentication set in SecurityContext");

                    if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        log.info("Current auth principal: {}",
                                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                        log.info("Current auth authorities: {}",
                                SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                    }
                } else {
                    log.warn("Username is null from token");
                }
            } else {
                log.warn("Token is null or invalid");
            }
        } catch (Exception e) {
            log.error("Error in JWT filter: {}", e.getMessage(), e);
        }

        log.info("JWT filter end -> method={} uri={} status={}", request.getMethod(), request.getRequestURI(), response.getStatus());
        filterChain.doFilter(request, response);
    }

    private Claims extractAllClaims(String token) {
        try {
            return jwtService.getAllClaims(token);
        } catch (Exception e) {
            log.error("Error extracting claims: {}", e.getMessage(), e);
            throw e;
        }
    }
}
