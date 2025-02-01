package com.crypto.controller;

import com.crypto.model.User;
import com.crypto.model.Wallet;
import com.crypto.service.UserService;
import com.crypto.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<List<Wallet>> getUserWallets(@RequestHeader("Authorization") String token) {
        // Validate token and get user
        // Then return wallets
        return ResponseEntity.ok().build();
    }
}