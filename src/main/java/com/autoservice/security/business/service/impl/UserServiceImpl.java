package com.autoservice.security.business.service.impl;

import com.autoservice.security.business.handlers.AuthenticateException;
import com.autoservice.security.business.mappers.UserMapStructMapper;
import com.autoservice.security.business.repository.UserDAO;
import com.autoservice.security.business.repository.UserRepository;
import com.autoservice.security.business.service.UserService;
import com.autoservice.security.models.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapStructMapper mapper;

    @Override
    @SneakyThrows
    public User saveUser(User user) {
        if (!hasNoMatch(user)) {
            log.error("User conflict exception is thrown: ");
            throw new AuthenticateException("Authenticate exception: there are user(s) " +
                    "with username " + user.getUsername() + " in database");
        }
        UserDAO userSaved = repository.save(mapper.userToUserDAO(user));
        log.info("New user saved: {}", () -> userSaved);
        return mapper.userDAOToUser(userSaved);
    }

    public Optional<User> findUserByUsername(String username){
       Optional<User> userByUsername = repository.findByUsername(username)
                .flatMap(userDAO -> Optional.ofNullable(mapper.userDAOToUser(userDAO)));
       log.info("User with username {} is {}", username, userByUsername);
       return userByUsername;
    }

    public boolean hasNoMatch(User user) {
        return repository.findAll().stream()
                .noneMatch(userDAO -> !userDAO.getId().equals(user.getId()) &&
                        userDAO.getUsername().equalsIgnoreCase(user.getUsername()));
    }
}
