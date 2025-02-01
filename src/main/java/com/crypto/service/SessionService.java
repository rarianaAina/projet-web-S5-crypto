package com.crypto.service;

import com.crypto.model.Session;
import com.crypto.model.User;
import com.crypto.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Transactional
    public Session creerSession(User user) {
        // Désactiver les sessions existantes
        sessionRepository.deleteByUser(user);

        Session session = new Session();
        session.setUser(user);
        session.setToken(UUID.randomUUID().toString());
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusHours(24));

        return sessionRepository.save(session);
    }

    public boolean verifierSession(String token) {
        return sessionRepository.findByTokenAndActiveTrue(token)
                .map(session -> !session.getExpiresAt().isBefore(LocalDateTime.now()))
                .orElse(false);
    }

    @Transactional
    public void terminerSession(String token) {
        sessionRepository.findByTokenAndActiveTrue(token)
                .ifPresent(session -> {
                    session.setActive(false);
                    sessionRepository.save(session);
                });
    }
}