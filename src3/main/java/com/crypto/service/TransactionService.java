package com.crypto.service;

import com.crypto.model.Transaction;
import com.crypto.model.User;
import com.crypto.model.Cryptocurrency;
import com.crypto.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final EmailService emailService;
    
    @Transactional
    public Transaction createTransaction(User user, Cryptocurrency crypto, 
                                      Transaction.TransactionType type, 
                                      BigDecimal amount, BigDecimal price) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCryptocurrency(crypto);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setPrice(price);
        transaction.setValidated(false);
        
        // Calculate commission (example: 1%)
        transaction.setCommission(amount.multiply(new BigDecimal("0.01")));
        
        Transaction saved = transactionRepository.save(transaction);
        emailService.sendTransactionNotification(user.getEmail(), saved);
        
        return saved;
    }
    
    @Transactional
    public Transaction validateTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        transaction.setValidated(true);
        
        // Update wallet balance based on transaction type
        BigDecimal amount = transaction.getAmount();
        if (transaction.getType() == Transaction.TransactionType.SELL) {
            amount = amount.negate();
        }
        
        walletService.updateWalletBalance(
            transaction.getUser(), 
            transaction.getCryptocurrency(), 
            amount
        );
        
        return transactionRepository.save(transaction);
    }
}