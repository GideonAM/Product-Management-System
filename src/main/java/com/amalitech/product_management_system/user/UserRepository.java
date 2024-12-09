package com.amalitech.product_management_system.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = """
            SELECT *
            FROM _user u
            WHERE u.email = ?1
            """, nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String userEmail);
}
