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

    public UserPortfolioSummary(String userId, String email, BigDecimal totalBuyAmount,
                                BigDecimal totalSellAmount, BigDecimal currentValue) {
        this.userId = userId;
        this.userEmail = email;
        this.totalBuys = totalBuyAmount;
        this.totalSells = totalSellAmount;
        this.portfolioValue = currentValue;
    }
}