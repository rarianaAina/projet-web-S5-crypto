package com.crypto.service;

import com.crypto.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    public void sendTransactionNotification(String email, Transaction transaction) {
        // Implementation for sending emails
        log.info("Sending transaction notification to: " + email);
    }
}