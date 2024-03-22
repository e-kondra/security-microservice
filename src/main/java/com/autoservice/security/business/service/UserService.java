package com.autoservice.security.business.service;

import com.autoservice.security.models.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findUserByUsername(String username);
}
