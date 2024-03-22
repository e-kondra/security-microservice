package com.autoservice.security.business.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<TokenDAO, Long> {

    @Query(" select t from TokenDAO t inner join UserDAO u " +
            "on t.user.id = u.id " +
            "where u.id = :id and (t.expired = false or t.revoked = false) ")
    List<TokenDAO> findAllValidTokenByUser(@Param("id") Long id);

    Optional<TokenDAO> findByToken(String token);
}
