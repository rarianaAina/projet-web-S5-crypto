package com.crypto.controller;

import com.crypto.model.Cryptocurrency;
import com.crypto.repository.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MarketController {
    
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    
    @GetMapping("/market")
    public String marketView(Model model) {
        List<Cryptocurrency> cryptocurrencies = cryptocurrencyRepository.findAll();
        model.addAttribute("cryptocurrencies", cryptocurrencies);
        return "market";
    }
    
    @GetMapping("/api/cryptocurrencies/prices")
    @ResponseBody
    public List<Cryptocurrency> getPrices() {
        return cryptocurrencyRepository.findAll();
    }
    
    @GetMapping("/trade/{id}")
    public String tradeView(@PathVariable Long id, Model model) {
        Cryptocurrency crypto = cryptocurrencyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Crypto non trouvée"));
        model.addAttribute("crypto", crypto);
        return "trade";
    }
}