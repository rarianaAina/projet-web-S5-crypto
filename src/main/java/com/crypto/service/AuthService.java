package com.crypto.service;

import com.crypto.controller.AuthController;
import com.crypto.model.User;
import com.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate authRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    public AuthController.AuthResponse login(String email, String password) {
        // Appel au service d'authentification externe
        var response = authRestTemplate.postForEntity(
                authServiceUrl + "/api/auth/login",
                new LoginRequest(email, password),
                AuthResponse.class
        );

        AuthResponse authResponse = response.getBody();

        // Créer ou mettre à jour l'utilisateur local
        User user = userRepository.findById(authResponse.getUserId())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setId(authResponse.getUserId());
                    newUser.setEmail(email);
                    newUser.setRole(authResponse.getRole());
                    return newUser;
                });

        userRepository.save(user);

        return new AuthController.AuthResponse(
                authResponse.getToken(),
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    public AuthController.AuthResponse register(String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Les mots de passe ne correspondent pas");
        }

        // Appel au service d'authentification externe
        var response = authRestTemplate.postForEntity(
                authServiceUrl + "/api/auth/register",
                new RegisterRequest(email, password),
                AuthResponse.class
        );

        AuthResponse authResponse = response.getBody();

        // Créer l'utilisateur local
        User user = new User();
        user.setId(authResponse.getUserId());
        user.setEmail(email);
        user.setRole("USER");
        userRepository.save(user);

        return new AuthController.AuthResponse(
                authResponse.getToken(),
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    private static class LoginRequest {


        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
        // getters
    }

    private static class RegisterRequest {
        private String email;
        private String password;

        public RegisterRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
        // getters
    }

    private static class AuthResponse {
        private String token;
        private String userId;
        private String email;
        private String role;
        // getters et setters


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}