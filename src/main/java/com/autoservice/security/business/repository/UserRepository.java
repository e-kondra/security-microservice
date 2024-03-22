package com.autoservice.security.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserDAO, Long> {

    Optional<UserDAO> findByUsername(String username);
}
