package com.crypto.repository;

import com.crypto.model.Session;
import com.crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndActiveTrue(String token);
    void deleteByUser(User user);
}