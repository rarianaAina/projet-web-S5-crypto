package com.crypto.controller;

import com.crypto.repository.UserRepository;
import com.crypto.service.EmailVerificationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/email")
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;
    private final UserRepository userRepository; // Ajoute ton UserRepository pour mettre à jour l'email vérifié

    public EmailVerificationController(EmailVerificationService emailVerificationService, UserRepository userRepository) {
        this.emailVerificationService = emailVerificationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean isVerified = emailVerificationService.isEmailVerified(email);

        if (isVerified) {
            // Mettre à jour la base de données si l'email est vérifié
            userRepository.updateEmailVerified(email, true);
        }

        return ResponseEntity.ok(isVerified);
    }
}
