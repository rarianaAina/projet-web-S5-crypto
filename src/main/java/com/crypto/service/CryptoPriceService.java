package com.crypto.service;

import com.crypto.model.Cryptocurrency;
import com.crypto.repository.CryptocurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class CryptoPriceService {
    
    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    
    private final Random random = new Random();
    
    @Scheduled(fixedRateString = "${crypto.price.update.interval}")
    public void updatePrices() {
        cryptocurrencyRepository.findAll().forEach(crypto -> {
            // Variation de prix entre -5% et +5%
            double variation = (random.nextDouble() * 10 - 5) / 100;
            BigDecimal newPrice = crypto.getCurrentPrice()
                    .multiply(BigDecimal.ONE.add(BigDecimal.valueOf(variation)))
                    .setScale(2); // HALF_UP implicite


            crypto.setCurrentPrice(newPrice);
            crypto.setLastUpdate(LocalDateTime.now());
            cryptocurrencyRepository.save(crypto);
        });
    }
}