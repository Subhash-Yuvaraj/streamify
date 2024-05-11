package com.audiophile.streamify.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String generateToken(UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    Object generateRefreshToken(Map<String, Object> claims, UserDetails userDetails);
}
