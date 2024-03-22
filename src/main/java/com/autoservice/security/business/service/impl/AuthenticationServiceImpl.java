package com.autoservice.security.business.service.impl;

import com.autoservice.security.business.handlers.AuthenticateException;
import com.autoservice.security.business.mappers.TokenMapStructMapper;
import com.autoservice.security.business.mappers.UserMapStructMapper;
import com.autoservice.security.business.repository.TokenRepository;
import com.autoservice.security.business.repository.UserDAO;
import com.autoservice.security.business.repository.UserRepository;
import com.autoservice.security.business.service.AuthenticationService;
import com.autoservice.security.business.service.JwtService;
import com.autoservice.security.business.service.UserService;
import com.autoservice.security.models.AuthenticationRequest;
import com.autoservice.security.models.AuthenticationResponse;
import com.autoservice.security.models.RegisterRequest;
import com.autoservice.security.models.Role;
import com.autoservice.security.models.Token;
import com.autoservice.security.models.TokenType;
import com.autoservice.security.models.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    @Autowired
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapStructMapper mapper;
    private final TokenMapStructMapper tokenMapper;
    @Autowired
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userService.saveUser(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(tokenMapper.tokenToTokenDAO(token));
    }

    @SneakyThrows
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (Exception e){
            throw new AuthenticateException("Authentication exception: User wasn't authenticated" + e.getMessage());
        }
        Optional<User> userOptional = userService.findUserByUsername(authenticationRequest.getUsername());
        if (userOptional.isEmpty()) {
            throw new AuthenticateException("Authentication exception: User with username "
                    +authenticationRequest.getUsername()+ " wasn't found");
        }
        User user = userOptional.get();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
