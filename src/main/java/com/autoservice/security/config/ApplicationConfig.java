package com.autoservice.security.config;

import com.autoservice.security.business.mappers.UserMapStructMapper;
import com.autoservice.security.business.repository.UserDAO;
import com.autoservice.security.business.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    @Autowired
    private UserMapStructMapper userMapper;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByUsername(username)
                .flatMap(userDAO -> Optional.of(userMapper.userDAOToUser(userDAO)))
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
