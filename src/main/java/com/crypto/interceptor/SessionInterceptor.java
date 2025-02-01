/*
package com.crypto.interceptor;

import com.crypto.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        // Si pas de token dans le header, chercher dans la session
        if (token == null) {
            token = (String) request.getSession().getAttribute("token");
        }

        if (token == null) {
            response.sendRedirect("/login");
            return false;
        }

        try {
            // Vérifier et récupérer l'utilisateur
            var user = userService.getCurrentUser(token);
            request.setAttribute("user", user);
            // Stocker le token en session
            request.getSession().setAttribute("token", token);
            return true;
        } catch (Exception e) {
            response.sendRedirect("/login");
            return false;
        }
    }
}*/
