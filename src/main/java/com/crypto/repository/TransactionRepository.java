package com.crypto.repository;

import com.crypto.model.Transaction;
import com.crypto.dto.UserPortfolioSummary;
import com.crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByTimestampDesc(User user);
    
    @Query("SELECT t FROM Transaction t WHERE t.timestamp BETWEEN :startDate AND :endDate " +
           "AND t.cryptocurrency.id IN :cryptoIds")
    List<Transaction> findByDateRangeAndCryptos(LocalDateTime startDate, 
                                              LocalDateTime endDate,
                                              List<Long> cryptoIds);
    
    @Query("SELECT new com.trading.crypto.dto.UserPortfolioSummary(" +
           "u.id, u.email, " +
           "SUM(CASE WHEN t.type = 'BUY' THEN t.amount * t.price ELSE 0 END), " +
           "SUM(CASE WHEN t.type = 'SELL' THEN t.amount * t.price ELSE 0 END), " +
           "SUM(CASE WHEN t.type = 'BUY' THEN t.amount ELSE -t.amount END) * c.currentPrice) " +
           "FROM Transaction t " +
           "JOIN t.user u " +
           "JOIN t.cryptocurrency c " +
           "WHERE t.timestamp <= :maxDate " +
           "GROUP BY u.id, u.email, c.currentPrice")
    List<UserPortfolioSummary> getUserPortfolioSummaries(LocalDateTime maxDate);
    
    long countByTimestampAfter(LocalDateTime timestamp);
    
    @Query("SELECT SUM(t.amount * t.price) FROM Transaction t WHERE t.timestamp > :timestamp")
    BigDecimal calculateVolumeAfter(LocalDateTime timestamp);
}