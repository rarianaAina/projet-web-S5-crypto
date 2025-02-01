package com.crypto.service;

import com.crypto.model.User;
import com.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Cette méthode recherche un utilisateur par email
    }
}