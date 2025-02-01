package com.crypto.controller;

import com.crypto.model.*;
import com.crypto.repository.*;
import com.crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DepositRequestRepository depositRequestRepository;

    @GetMapping
    public String viewPortfolio(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("portfolio", portfolioRepository.findByUser(user));
        return "portfolio";
    }

    @PostMapping("/deposit")
    @ResponseBody
    public DepositRequest requestDeposit(HttpSession session, @RequestParam BigDecimal amount) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        DepositRequest request = new DepositRequest();
        request.setUser(user);
        request.setAmount(amount);
        request.setType(DepositRequest.RequestType.DEPOSIT);
        request.setStatus(DepositRequest.RequestStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());

        return depositRequestRepository.save(request);
    }

    @PostMapping("/withdraw")
    @ResponseBody
    public DepositRequest requestWithdrawal(HttpSession session, @RequestParam BigDecimal amount) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        if (user.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Solde insuffisant");
        }

        DepositRequest request = new DepositRequest();
        request.setUser(user);
        request.setAmount(amount);
        request.setType(DepositRequest.RequestType.WITHDRAWAL);
        request.setStatus(DepositRequest.RequestStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());

        return depositRequestRepository.save(request);
    }
}