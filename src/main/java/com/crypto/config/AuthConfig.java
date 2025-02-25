package com.crypto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthConfig {
    
/*    @Value("${auth.service.url}")
    private String authServiceUrl;
    
    @Value("${auth.service.client-id}")
    private String clientId;
    
    @Value("${auth.service.client-secret}")
    private String clientSecret;
    */
    @Bean
    public RestTemplate authRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setBearerAuth(getServiceToken());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
    
    private String getServiceToken() {
        // Obtenir un token de service pour les communications inter-services
        return "service-token";
    }
}
