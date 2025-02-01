package com.crypto.controller;

import com.crypto.model.User;
import com.crypto.repository.UserRepository;
import com.crypto.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/buy/{id}")
    public String buyCrypto(@PathVariable Long id,
                            @RequestParam BigDecimal amount,
                            @RequestParam Long userId,
                            Model model) {
        try {
            User user = userRepository.findById(String.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not found"));
            tradeService.buy(user, id, amount);
            model.addAttribute("message", "Achat réussi !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'achat : " + e.getMessage());
        }
        return "redirect:/trade/" + id; // Retourne à la page de trading
    }

    @PostMapping("/sell/{id}")
    public String sellCrypto(@PathVariable Long id,
                             @RequestParam BigDecimal amount,
                             @RequestParam Long userId,
                             Model model) {
        try {
            User user = userRepository.findById(String.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not found"));
            tradeService.sell(user, id, amount);
            model.addAttribute("message", "Vente réussie !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la vente : " + e.getMessage());
        }
        return "redirect:/trade/" + id; // Retourne à la page de trading
    }
}
