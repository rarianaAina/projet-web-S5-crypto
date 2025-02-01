package com.crypto.controller;

import com.crypto.model.Transaction;
import com.crypto.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    
    @PostMapping("/validate/{id}")
    public ResponseEntity<Transaction> validateTransaction(@PathVariable Long id) {
        Transaction validated = transactionService.validateTransaction(id);
        return ResponseEntity.ok(validated);
    }
}