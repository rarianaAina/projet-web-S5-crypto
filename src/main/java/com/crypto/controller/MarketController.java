package com.crypto.controller;

import com.crypto.model.Cryptocurrency;
import com.crypto.model.User;
import com.crypto.repository.CryptocurrencyRepository;
import com.crypto.service.UserService;
import com.crypto.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class MarketController {

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TradeService tradeService;

    @GetMapping("/market")
    public String marketView(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);
        List<Cryptocurrency> cryptocurrencies = cryptocurrencyRepository.findAll();

        model.addAttribute("cryptocurrencies", cryptocurrencies);
        model.addAttribute("user", user); // L'utilisateur est passé à la vue
        return "market"; // Cela retournera la vue "market.jsp" ou "market.html"
    }


    @PostMapping("/api/trade/buy")
    @ResponseBody
    public ResponseEntity<?> buy(
            HttpSession session,
            @RequestParam Long cryptoId,
            @RequestParam BigDecimal amount) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            User user = userService.getUserById(userId);
            tradeService.buy(user, cryptoId, amount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/api/trade/sell")
    @ResponseBody
    public ResponseEntity<?> sell(
            HttpSession session,
            @RequestParam Long cryptoId,
            @RequestParam BigDecimal amount) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            User user = userService.getUserById(userId);
            tradeService.sell(user, cryptoId, amount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}