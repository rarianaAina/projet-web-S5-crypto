package com.crypto.controller;

import com.crypto.model.Cryptocurrency;
import com.crypto.repository.CryptocurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
@RequiredArgsConstructor
public class CryptoController {
    private final CryptocurrencyRepository cryptocurrencyRepository;
    
    @GetMapping
    public ResponseEntity<List<Cryptocurrency>> getAllCryptocurrencies() {
        return ResponseEntity.ok(cryptocurrencyRepository.findAll());
    }
}