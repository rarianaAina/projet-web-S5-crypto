package com.crypto.repository;

import com.crypto.model.Portfolio;
import com.crypto.model.User;
import com.crypto.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    Optional<Portfolio> findByUserAndCryptocurrency(User user, Cryptocurrency cryptocurrency);
}