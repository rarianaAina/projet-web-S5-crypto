package com.crypto.service;

import com.crypto.model.Wallet;
import com.crypto.model.User;
import com.crypto.model.Cryptocurrency;
import com.crypto.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    
    public List<Wallet> getUserWallets(User user) {
        return walletRepository.findByUser(user);
    }
    
    @Transactional
    public Wallet updateWalletBalance(User user, Cryptocurrency crypto, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserAndCryptocurrency(user, crypto)
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet();
                    newWallet.setUser(user);
                    newWallet.setCryptocurrency(crypto);
                    newWallet.setBalance(BigDecimal.ZERO);
                    return newWallet;
                });
        
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }
}