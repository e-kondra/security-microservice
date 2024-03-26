package com.autoservice.security.business.service.impl;

import com.autoservice.security.business.mappers.TokenMapStructMapper;
import com.autoservice.security.business.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutHandler {

    final TokenRepository tokenRepository;
    final TokenMapStructMapper mapper;
    @Override
    public void logout(
            HttpServletRequest request
            , HttpServletResponse response
            , Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .flatMap(tokenDAO -> Optional.ofNullable(mapper.tokenDAOToToken(tokenDAO)))
                .orElse(null);
        if(storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(mapper.tokenToTokenDAO(storedToken));
            SecurityContextHolder.clearContext();
        }
    }
}
