package com.crypto.service;

import com.crypto.controller.AuthController;
import com.crypto.model.User;
import com.crypto.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Service
public class AuthService {

    @Autowired
    private RestTemplate authRestTemplate;

    @Autowired
    private UserRepository userRepository;

    @Value("${auth.service.url}")
    private String authServiceUrl;

/*
    public AuthController.AuthResponse login(String email, String password) throws JsonProcessingException {
        System.out.println("Méthode login appelée avec : " + email + " / " + password);
        // Appel au service d'authentification externe
        ResponseEntity<String> response = authRestTemplate.postForEntity(
                authServiceUrl + "/api/auth/login",
                new LoginRequest(email, password),
                String.class
        );

        System.out.println("Réponse brute reçue : " + response.getBody());

// Convertir manuellement en JSON
        ObjectMapper objectMapper = new ObjectMapper();
        AuthResponse authResponse = objectMapper.readValue(response.getBody(), AuthResponse.class);

*/
/*
        System.out.println("Réponse reçue : " + response.getBody());
        *//*


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
*/

/*    public AuthController.AuthResponse login(String email, String password) throws JsonProcessingException {
        System.out.println("Méthode login appelée avec : " + email + " / " + password);

        // Appel au service d'authentification externe
        ResponseEntity<String> response = authRestTemplate.postForEntity(
                authServiceUrl + "/api/auth/login",
                new LoginRequest(email, password),
                String.class
        );

        System.out.println("Réponse brute reçue : " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        // Vérifier si la réponse contient "message"
        if (response.getBody().contains("\"message\"")) {
            System.out.println("Message reçu : " + response.getBody());
            return null; // Ou une exception personnalisée
        }

        AuthResponse authResponse = objectMapper.readValue(response.getBody(), AuthResponse.class);

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
    }*/

    public String login(String email, String password) throws JsonProcessingException {
        System.out.println("Méthode login appelée avec : " + email + " / " + password);

        ResponseEntity<String> response = authRestTemplate.postForEntity(
                authServiceUrl + "/api/auth/login",
                new LoginRequest(email, password),
                String.class
        );

        System.out.println("Réponse brute reçue : " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        // Vérifier si la réponse contient "message"
        if (response.getBody().contains("\"message\"")) {
            System.out.println("Message reçu : " + response.getBody());

            // Retourner directement le message de la réponse
            return response.getBody();
        }

        // Si la réponse ne contient pas de message, traiter la réponse JSON normalement
        AuthResponse authResponse = objectMapper.readValue(response.getBody(), AuthResponse.class);

        User user = userRepository.findById(authResponse.getUserId())
                .orElseGet(() -> {
                    User newUser = new User();
                    //newUser.setId(authResponse.getUserId());
                    newUser.setEmail(email);
                    newUser.setRole(authResponse.getRole());
                    return newUser;
                });

        //userRepository.save(user);

        // Retourner la réponse du serveur si aucune erreur
        return response.getBody();  // Renvoie la réponse authentifiée
    }




    public String register(String email, String password, String confirmPassword, String firstName, String lastName) {
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Les mots de passe ne correspondent pas");
        }

        // Appel au service d'authentification externe
        var response = authRestTemplate.postForEntity(
                authServiceUrl + "/api/auth/signup",
                new RegisterRequest(email, password, firstName, lastName),
                String.class
        );
        System.out.println("Tonga eto 1");
        System.out.println("Tonga eto 2");
        // Créer l'utilisateur local
        User user = new User();
        //user.setId(authResponse.getUserId());
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole("USER");
        userRepository.save(user);

        String nety = "Nety";
        return nety;
    }

/*    public String verifyPin(String email, String pin) {
        // Appel au service d'authentification externe
        ResponseEntity<String> response = authRestTemplate.exchange(
                authServiceUrl + "/api/auth/verify-2fa/" + email + "/" + pin,
                HttpMethod.POST,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );

        return response.getBody();
    }*/


    public String verifyPin(String email, String pin) {
        // Appel au service d'authentification externe
        ResponseEntity<String> response = authRestTemplate.exchange(
                authServiceUrl + "/api/auth/verify-2fa/" + email + "/" + pin,
                HttpMethod.POST,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );

        // Si la réponse est validée
        if (response.getStatusCode() == HttpStatus.OK) {
            // Rediriger vers la page market.jsp
            String nety = "Nety";
            return nety;
        } else {
            // Retourner un message d'erreur ou une redirection vers une autre page
            String tsyNety = "Tsy nety";
            return tsyNety;
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
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

    public class RegisterRequest {

        @JsonProperty("email")
        private String email;

        @JsonProperty("password")
        private String password;

        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("lastName")
        private String lastName;
        // Constructeur, getters et setters

        public RegisterRequest(String email, String password, String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
        }

        // Getters et setters
    }

    private static class AuthResponse {

        private String message;

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

        public  void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}