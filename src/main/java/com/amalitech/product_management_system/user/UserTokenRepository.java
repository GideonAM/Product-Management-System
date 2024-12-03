package com.amalitech.product_management_system.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, String > {
    Optional<UserToken> findByToken(String token);
}
