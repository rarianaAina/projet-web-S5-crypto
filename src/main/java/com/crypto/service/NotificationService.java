package com.crypto.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
public class NotificationService {
    
    @Value("${notification.service.url}")
    private String notificationServiceUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    public void sendMobileNotification(Long userId, String message) {
        NotificationRequest request = new NotificationRequest(userId, message);
        restTemplate.postForEntity(notificationServiceUrl + "/notify", request, Void.class);
    }
    
    private static class NotificationRequest {
        private Long userId;
        private String message;
        
        public NotificationRequest(Long userId, String message) {
            this.userId = userId;
            this.message = message;
        }
        
        // getters et setters
    }
}