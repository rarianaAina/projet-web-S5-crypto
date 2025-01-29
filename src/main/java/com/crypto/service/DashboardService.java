package com.crypto.service;

import com.crypto.dto.DashboardStats;
import com.crypto.repository.TransactionRepository;
import com.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class DashboardService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    public DashboardStats getStats() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        
        DashboardStats stats = new DashboardStats();
        stats.setActiveUsers(userRepository.count());
        stats.setTodayTransactions(transactionRepository.countByTimestampAfter(yesterday));
        stats.setDailyVolume(transactionRepository.calculateVolumeAfter(yesterday));
        
        return stats;
    }
}