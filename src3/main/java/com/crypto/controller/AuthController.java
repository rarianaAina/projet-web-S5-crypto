package com.crypto.controller;

import com.crypto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RestTemplate restTemplate;
    
    @PostMapping("/sync")
    public ResponseEntity<?> syncUser(@RequestHeader("Authorization") String token) {
        // Call external auth service to validate token and get user info
        // Then sync with our database
        return ResponseEntity.ok().build();
    }
}