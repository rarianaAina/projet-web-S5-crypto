package com.crypto.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DashboardStats {
    private long activeUsers;
    private long todayTransactions;
    private BigDecimal dailyVolume;
}