package com.crypto.controller;

import com.crypto.model.DepositRequest;
import com.crypto.model.User;
import com.crypto.service.DepositRequestService;
import com.crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class DepositController {

    @Autowired
    private DepositRequestService depositRequestService;

    @Autowired
    private UserService userService;

    @GetMapping("/deposit")
    public String showDepositPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId); // Récupère l'utilisateur via l'ID de la session
        model.addAttribute("user", user);
        return "deposit";
    }

    @GetMapping("/withdraw")
    public String showWithdrawPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId); // Récupère l'utilisateur via l'ID de la session
        model.addAttribute("user", user);
        return "retrait";
    }

    @PostMapping("/api/deposit")
    @ResponseBody
    public DepositRequest requestDeposit(HttpSession session, @RequestParam BigDecimal amount) {
        Long userId = (Long) session.getAttribute("userId"); // Récupère l'ID de l'utilisateur depuis la session
        User user = userService.getUserById(userId); // Récupère l'utilisateur via l'ID de la session
        return depositRequestService.creerDemande(user, amount, DepositRequest.RequestType.DEPOSIT);
    }

    @PostMapping("/api/withdraw")
    @ResponseBody
    public DepositRequest requestWithdraw(HttpSession session, @RequestParam BigDecimal amount) {
        Long userId = (Long) session.getAttribute("userId"); // Récupère l'ID de l'utilisateur depuis la session
        User user = userService.getUserById(userId); // Récupère l'utilisateur via l'ID de la session
        return depositRequestService.creerDemande(user, amount, DepositRequest.RequestType.WITHDRAWAL);
    }
}
