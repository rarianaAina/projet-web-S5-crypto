package com.crypto.service;

import com.crypto.model.DepositRequest;
import com.crypto.model.User;
import com.crypto.repository.DepositRequestRepository;
import com.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepositRequestService {
    
    @Autowired
    private DepositRequestRepository depositRequestRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;
    
    public List<DepositRequest> getPendingRequests() {
        return depositRequestRepository.findByStatusOrderByCreatedAtDesc(
            DepositRequest.RequestStatus.PENDING);
    }
    
    @Transactional
    public DepositRequest processRequest(Long requestId, DepositRequest.RequestStatus status) {
        DepositRequest request = depositRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Demande non trouvée"));
            
        if (request.getStatus() != DepositRequest.RequestStatus.PENDING) {
            throw new RuntimeException("Cette demande a déjà été traitée");
        }
        
        request.setStatus(status);
        request.setProcessedAt(LocalDateTime.now());
        
        if (status == DepositRequest.RequestStatus.APPROVED) {
            User user = request.getUser();
            if (request.getType() == DepositRequest.RequestType.DEPOSIT) {
                user.setBalance(user.getBalance().add(request.getAmount()));
            } else {
                user.setBalance(user.getBalance().subtract(request.getAmount()));
            }
            userRepository.save(user);
        }
        
        // Envoyer une notification mobile
        String message = String.format(
            "Votre demande de %s de %s € a été %s",
            request.getType() == DepositRequest.RequestType.DEPOSIT ? "dépôt" : "retrait",
            request.getAmount(),
            status == DepositRequest.RequestStatus.APPROVED ? "approuvée" : "rejetée"
        );
        notificationService.sendMobileNotification(request.getUser().getId(), message);
        
        return depositRequestRepository.save(request);
    }
}