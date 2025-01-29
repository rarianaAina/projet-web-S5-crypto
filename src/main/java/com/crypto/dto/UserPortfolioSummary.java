package com.crypto.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class UserPortfolioSummary {
    private String userId;
    private String userEmail;
    private BigDecimal totalBuys;
    private BigDecimal totalSells;
    private BigDecimal portfolioValue;
}