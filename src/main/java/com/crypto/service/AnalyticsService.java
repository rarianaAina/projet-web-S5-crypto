package com.crypto.service;

import com.crypto.dto.UserPortfolioSummary;
import com.crypto.model.*;
import com.crypto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    public Map<String, BigDecimal> calculateStatistics(
            List<Long> cryptoIds,
            String analysisType,
            LocalDateTime startDate,
            LocalDateTime endDate) {

        List<Transaction> transactions = transactionRepository.findByDateRangeAndCryptos(
                startDate, endDate, cryptoIds);

        Map<String, BigDecimal> results = new HashMap<>();

        for (Long cryptoId : cryptoIds) {
            List<BigDecimal> prices = transactions.stream()
                    .filter(t -> t.getCryptocurrency().getId().equals(cryptoId))
                    .map(Transaction::getPrice)
                    .collect(Collectors.toList());

            switch (analysisType.toLowerCase()) {
                case "min":
                    results.put("min_" + cryptoId,
                            prices.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
                    break;
                case "max":
                    results.put("max_" + cryptoId,
                            prices.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
                    break;
                case "moyenne":
                    if (!prices.isEmpty()) {
                        BigDecimal sum = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal count = new BigDecimal(prices.size());
                        results.put("avg_" + cryptoId, sum.divide(count));
                    }
                    break;
                case "ecart-type":
                    if (!prices.isEmpty()) {
                        BigDecimal mean = prices.stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(new BigDecimal(prices.size()));

                        BigDecimal variance = prices.stream()
                                .map(p -> p.subtract(mean).pow(2))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(new BigDecimal(prices.size()));

                        results.put("std_" + cryptoId,
                                new BigDecimal(Math.sqrt(variance.doubleValue())));
                    }
                    break;
                case "quartile":
                    List<BigDecimal> sortedPrices = new ArrayList<>(prices);
                    Collections.sort(sortedPrices);
                    int q1Index = sortedPrices.size() / 4;
                    results.put("q1_" + cryptoId,
                            sortedPrices.isEmpty() ? BigDecimal.ZERO : sortedPrices.get(q1Index));
                    break;
            }
        }

        return results;
    }

    public Map<String, BigDecimal> getCommissionAnalytics(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<Long> cryptoIds) {

        List<Transaction> transactions = transactionRepository.findByDateRangeAndCryptos(
                startDate, endDate, cryptoIds);

        BigDecimal totalCommissions = BigDecimal.ZERO;
        int transactionCount = transactions.size();

        Commission currentCommission = commissionRepository
                .findFirstByActiveOrderByCreatedAtDesc(true)
                .orElseThrow(() -> new RuntimeException("Aucune commission active trouvée"));

        for (Transaction transaction : transactions) {
            BigDecimal commissionRate = transaction.getType() == Transaction.TransactionType.BUY ?
                    currentCommission.getBuyCommission() : currentCommission.getSellCommission();

            totalCommissions = totalCommissions.add(
                    transaction.getAmount()
                            .multiply(transaction.getPrice())
                            .multiply(commissionRate)
                            .divide(new BigDecimal(100))
            );
        }

        Map<String, BigDecimal> analytics = new HashMap<>();
        analytics.put("total", totalCommissions);
        analytics.put("average", transactionCount > 0 ?
                totalCommissions.divide(new BigDecimal(transactionCount)) :
                BigDecimal.ZERO);

        return analytics;
    }

    public List<UserPortfolioSummary> getPortfolioSummary(LocalDateTime maxDate) {
        return transactionRepository.getUserPortfolioSummaries(maxDate);
    }
}