package com.crypto.service;

import com.crypto.model.User;
import com.crypto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    @Transactional
    public User createOrUpdateUser(String externalId, String email) {
        return userRepository.findByExternalId(externalId)
                .map(user -> {
                    user.setEmail(email);
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setExternalId(externalId);
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });
    }
}