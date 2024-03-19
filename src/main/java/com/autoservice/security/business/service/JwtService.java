package com.autoservice.security.business.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String jwtToken);

    boolean isTokenValid(String token, UserDetails userDetails);
}
