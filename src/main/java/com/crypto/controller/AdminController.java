package com.crypto.controller;

import com.crypto.model.DepositRequest;
import com.crypto.model.User;
import com.crypto.service.DepositRequestService;
import com.crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DepositRequestService depositRequestService;

    @Autowired
    private UserService userService;

    @GetMapping("/demandes")
    public String afficherDemandes(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/market";
        }

        model.addAttribute("demandesEnAttente",
                depositRequestService.obtenirDemandesEnAttente());
        return "admin/demandes";
    }

    @PostMapping("/demandes/{id}/traiter")
    @ResponseBody
    public ResponseEntity<DepositRequest> traiterDemande(
            @PathVariable Long id,
            @RequestParam DepositRequest.RequestStatus statut,
            HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        if (!"ADMIN".equals(user.getRole())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(depositRequestService.traiterDemande(id, statut));
    }
}