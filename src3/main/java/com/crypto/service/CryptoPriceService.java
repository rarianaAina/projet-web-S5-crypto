package com.crypto.service;

import com.crypto.model.Cryptocurrency;
import com.crypto.model.CryptoPrice;
import com.crypto.repository.CryptocurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoPriceService {
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final RestTemplate restTemplate;
    
    public void updatePrices() {
        List<Cryptocurrency> cryptos = cryptocurrencyRepository.findAll();
        for (Cryptocurrency crypto : cryptos) {
            // Simulate price update from external API
            BigDecimal newPrice = fetchPriceFromExternalAPI(crypto.getSymbol());
            crypto.setCurrentPrice(newPrice);
            cryptocurrencyRepository.save(crypto);
        }
    }
    
    private BigDecimal fetchPriceFromExternalAPI(String symbol) {
        // Implementation to fetch real prices from a crypto API
        // This is a placeholder that returns a random price
        return BigDecimal.valueOf(Math.random() * 50000);
    }
}