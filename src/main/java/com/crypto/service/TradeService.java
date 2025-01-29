package com.crypto.service;

import com.crypto.model.*;
import com.crypto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TradeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public void buy(User user, Long cryptoId, BigDecimal amount) {
        Cryptocurrency crypto = cryptocurrencyRepository.findById(cryptoId)
                .orElseThrow(() -> new RuntimeException("Crypto non trouvée"));

        BigDecimal totalCost = crypto.getCurrentPrice().multiply(amount);

        if (user.getBalance().compareTo(totalCost) < 0) {
            throw new RuntimeException("Solde insuffisant");
        }

        // Mise à jour du solde
        user.setBalance(user.getBalance().subtract(totalCost));
        userRepository.save(user);

        // Mise à jour du portfolio
        Portfolio portfolio = portfolioRepository.findByUserAndCryptocurrency(user, crypto)
                .orElse(new Portfolio());

        if (portfolio.getId() == null) {
            portfolio.setUser(user);
            portfolio.setCryptocurrency(crypto);
            portfolio.setAmount(BigDecimal.ZERO);
        }

        portfolio.setAmount(portfolio.getAmount().add(amount));
        portfolioRepository.save(portfolio);

        // Enregistrement de la transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCryptocurrency(crypto);
        transaction.setAmount(amount);
        transaction.setPrice(crypto.getCurrentPrice());
        transaction.setType(Transaction.TransactionType.BUY);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Transactional
    public void sell(User user, Long cryptoId, BigDecimal amount) {
        Cryptocurrency crypto = cryptocurrencyRepository.findById(cryptoId)
                .orElseThrow(() -> new RuntimeException("Crypto non trouvée"));

        Portfolio portfolio = portfolioRepository.findByUserAndCryptocurrency(user, crypto)
                .orElseThrow(() -> new RuntimeException("Pas de crypto dans le portfolio"));

        if (portfolio.getAmount().compareTo(amount) < 0) {
            throw new RuntimeException("Montant insuffisant dans le portfolio");
        }

        BigDecimal totalValue = crypto.getCurrentPrice().multiply(amount);

        // Mise à jour du solde
        user.setBalance(user.getBalance().add(totalValue));
        userRepository.save(user);

        // Mise à jour du portfolio
        portfolio.setAmount(portfolio.getAmount().subtract(amount));
        portfolioRepository.save(portfolio);

        // Enregistrement de la transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCryptocurrency(crypto);
        transaction.setAmount(amount);
        transaction.setPrice(crypto.getCurrentPrice());
        transaction.setType(Transaction.TransactionType.SELL);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}