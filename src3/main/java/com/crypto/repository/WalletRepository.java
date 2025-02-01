package com.crypto.repository;

import com.crypto.model.Wallet;
import com.crypto.model.User;
import com.crypto.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByUser(User user);
    Optional<Wallet> findByUserAndCryptocurrency(User user, Cryptocurrency cryptocurrency);
}