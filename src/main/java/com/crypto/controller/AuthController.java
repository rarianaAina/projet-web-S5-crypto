package com.crypto.controller;

import com.crypto.dto.RegisterRequest;
import com.crypto.model.User;
import com.crypto.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login"; // Cela retournera la vue `login.html` ou `login.jsp`
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "auth/register"; // Cela retournera la vue `login.html` ou `login.jsp`
    }

    @GetMapping("/verify-pin-2fa")
    public String showTwoFactorPage() {
        return "auth/verify-pin"; // Cela retournera la vue `verify-pin-2fa.html` ou `verify-pin-2fa.jsp`
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Supprimer l'attribut de session
        session.invalidate(); // Cela invalide toute la session et supprime les informations utilisateur
        return "redirect:/login"; // Rediriger vers la page de connexion ou d'accueil
    }

/*    @PostMapping("/verify-pin-2fa")
    @ResponseBody
    public String verifyPin(@RequestBody VerifyPinRequest request) {
        return authService.verifyPin(request.getEmail(), request.getPin());
    }*/

/*    @PostMapping("/verify-pin-2fa")
    @ResponseBody
    public String verifyPin(@RequestParam String email, @RequestParam String pin) {
        return authService.verifyPin(email, pin);
    }*/


    @PostMapping("/verify-pin-2fa")
    public String verifyPin(@RequestParam String email, @RequestParam String pin, HttpSession session) {
        // Appel au service d'authentification externe pour vérifier le PIN
        String verificationResponse = String.valueOf(authService.verifyPin(email, pin));

        // Si la vérification du PIN est réussie
        if ("Nety".equals(verificationResponse)) {
            System.out.println("nety");

            // Récupérer l'utilisateur par email et l'ajouter à la session
            User user = authService.getUserByEmail(email); // Assurez-vous que ce service existe et récupère l'utilisateur
            session.setAttribute("userId", user.getId()); // Stocker l'ID de l'utilisateur dans la session

            // Rediriger vers le marché
            return "redirect:/market"; // Cela redirigera vers le contrôleur du marché
        } else {
            System.out.println("Tsy nety");
            // Rediriger vers une page d'erreur si la vérification échoue
            return "error";
        }
    }


    @PostMapping("/api/auth/login")
    @ResponseBody
    public String login(@RequestBody LoginRequest request) throws JsonProcessingException {
        System.out.println("Méthode login appelée avec : " + request.getEmail() + " / " + request.getPassword());
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/api/auth/register")
    public String register(@RequestBody RegisterRequest request) {
        // Vérifiez les mots de passe ici
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "error";  // Vous pouvez rediriger vers une page d'erreur
        }

        // Appelez le service pour l'inscription
        return authService.register(request.getEmail(), request.getPassword(), request.getConfirmPassword(), request.getFirstName(), request.getLastName());
    }


    @GetMapping("/validate-email")
    public String validateEmail(@RequestParam("email") String email, Model model) {
        // Envoie l'email de l'utilisateur à la page JSP pour la validation
        model.addAttribute("email", email);
        return "validation-email"; // Affiche la page validation-email.jsp
    }



    public static class LoginRequest {
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

        private String email;
        private String password;
        // getters et setters
    }


    public static class AuthResponse {
        public AuthResponse(String token, String id, String email, String role) {
        }

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


        private String token;
        private String userId;
        private String email;
        private String role;


        // getters et setters
    }
}