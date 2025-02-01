package com.crypto.service;

import com.crypto.model.DepositRequest;
import com.crypto.model.User;
import com.crypto.repository.DepositRequestRepository;
import com.crypto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    public DepositRequest creerDemande(User user, BigDecimal amount, DepositRequest.RequestType type) {
        if (type == DepositRequest.RequestType.WITHDRAWAL && user.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Solde insuffisant pour ce retrait");
        }

        DepositRequest request = new DepositRequest();
        request.setUser(user);
        request.setAmount(amount);
        request.setType(type);
        request.setStatus(DepositRequest.RequestStatus.PENDING);
        request.setCreatedAt(LocalDateTime.now());

        return depositRequestRepository.save(request);
    }

    public List<DepositRequest> obtenirDemandesEnAttente() {
        return depositRequestRepository.findByStatusOrderByCreatedAtDesc(
                DepositRequest.RequestStatus.PENDING);
    }

    @Transactional
    public DepositRequest traiterDemande(Long demandeId, DepositRequest.RequestStatus statut) {
        DepositRequest demande = depositRequestRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        if (demande.getStatus() != DepositRequest.RequestStatus.PENDING) {
            throw new RuntimeException("Cette demande a déjà été traitée");
        }

        demande.setStatus(statut);
        demande.setProcessedAt(LocalDateTime.now());

        if (statut == DepositRequest.RequestStatus.APPROVED) {
            User utilisateur = demande.getUser();
            if (demande.getType() == DepositRequest.RequestType.DEPOSIT) {
                utilisateur.setBalance(utilisateur.getBalance().add(demande.getAmount()));
            } else {
                utilisateur.setBalance(utilisateur.getBalance().subtract(demande.getAmount()));
            }
            userRepository.save(utilisateur);
        }

        String message = String.format(
                "Votre demande de %s de %s € a été %s",
                demande.getType() == DepositRequest.RequestType.DEPOSIT ? "dépôt" : "retrait",
                demande.getAmount(),
                statut == DepositRequest.RequestStatus.APPROVED ? "approuvée" : "rejetée"
        );
        notificationService.sendMobileNotification(demande.getUser().getId(), message);

        return depositRequestRepository.save(demande);
    }
}