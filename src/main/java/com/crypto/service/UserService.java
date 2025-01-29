package com.crypto.service;

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
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${auth.service.url}")
    private String authServiceUrl;
    
    public User getCurrentUser(String token) {
        // Appel au service d'authentification externe
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        
        var response = restTemplate.exchange(
            authServiceUrl + "/api/user",
            HttpMethod.GET,
            requestEntity,
            UserDTO.class
        );
        
        UserDTO userDTO = response.getBody();
        return userRepository.findById(userDTO.getId())
            .orElseGet(() -> createUser(userDTO));
    }
    
    private User createUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }
    
    private static class UserDTO {
        private String id;
        private String email;
        private String role;
        // getters et setters

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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