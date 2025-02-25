package com.crypto.service;

import com.crypto.model.Cryptocurrency;
import com.crypto.model.CryptoHistory;
import com.crypto.repository.CryptocurrencyRepository;
import com.crypto.repository.CryptoHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CryptoPriceService {

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private CryptoHistoryRepository cryptoHistoryRepository;

    private final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(CryptoPriceService.class);

    @Scheduled(fixedRate = 10000) // Mise à jour toutes les 10 secondes
    public void mettreAJourPrix() {
        System.out.println("Exécution de mettreAJourPrix()...");

        cryptocurrencyRepository.findAll().forEach(crypto -> {
            double variation = (random.nextDouble() * 10 - 5) / 100; // Variation entre -5% et +5%
/*            BigDecimal nouveauPrix = crypto.getCurrentPrice()
                    .multiply(BigDecimal.ONE.add(BigDecimal.valueOf(variation)))
                    .setScale(2);*/

            BigDecimal nouveauPrix = crypto.getCurrentPrice()
                    .multiply(BigDecimal.ONE.add(BigDecimal.valueOf(variation)))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            crypto.setCurrentPrice(nouveauPrix);
            crypto.setLastUpdate(LocalDateTime.now());
            cryptocurrencyRepository.save(crypto);

            // Enregistrer l'historique
            CryptoHistory history = new CryptoHistory();
            history.setCryptocurrency(crypto);
            history.setPrice(nouveauPrix);
            history.setTimestamp(LocalDateTime.now());

            cryptoHistoryRepository.save(history);

            //System.out.println("Ajout en base : " + history.getCryptocurrency().getName() + " - " + history.getPrice());
        });
    }
}