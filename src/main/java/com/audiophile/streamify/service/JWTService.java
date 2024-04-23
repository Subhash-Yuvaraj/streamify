package com.audiophile.streamify.service;

import com.audiophile.streamify.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String generateToken(UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    Object generateRefershToken(Map<String, Object> claims, UserDetails userDetails);
}
