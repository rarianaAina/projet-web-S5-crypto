package com.crypto.repository;

import com.crypto.model.Transaction;
import com.crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByValidatedFalse();
}