package com.crypto.repository;

import com.crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.emailVerified = :verified WHERE u.email = :email")
    void updateEmailVerified(String email, boolean verified);
}