package com.crypto.repository;

import com.crypto.model.CryptoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoHistoryRepository extends JpaRepository<CryptoHistory, Long> {
}