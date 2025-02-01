package com.crypto.scheduler;

import com.crypto.service.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceUpdateScheduler {
    private final CryptoPriceService cryptoPriceService;
    
    @Scheduled(fixedRate = 10000) // Update every 10 seconds
    public void updatePrices() {
        cryptoPriceService.updatePrices();
    }
}