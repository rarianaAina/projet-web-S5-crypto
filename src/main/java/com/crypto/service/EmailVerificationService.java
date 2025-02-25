package com.crypto.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Service
public class EmailVerificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String authProviderUrl = "http://localhost:8443/api/auth/check-email"; // URL de ton fournisseur d'authentification

    public boolean isEmailVerified(String email) {
        String url = authProviderUrl + "?email=" + email;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getBody() != null) {
            return (Boolean) response.getBody().get("emailVerified");
        }
        return false;
    }
}
