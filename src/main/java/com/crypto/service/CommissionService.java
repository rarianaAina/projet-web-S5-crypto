package com.crypto.service;

import com.crypto.model.Commission;
import com.crypto.repository.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CommissionService {
    
    @Autowired
    private CommissionRepository commissionRepository;
    
    public Commission getCurrentCommission() {
        return commissionRepository.findFirstByActiveOrderByCreatedAtDesc(true)
            .orElseThrow(() -> new RuntimeException("Aucune commission active trouvée"));
    }
    
    @Transactional
    public Commission updateCommissions(BigDecimal buyCommission, BigDecimal sellCommission) {
        // Désactiver l'ancienne commission
        Commission currentCommission = getCurrentCommission();
        currentCommission.setActive(false);
        commissionRepository.save(currentCommission);
        
        // Créer une nouvelle commission
        Commission newCommission = new Commission();
        newCommission.setBuyCommission(buyCommission);
        newCommission.setSellCommission(sellCommission);
        newCommission.setActive(true);
        newCommission.setCreatedAt(LocalDateTime.now());
        
        return commissionRepository.save(newCommission);
    }
}