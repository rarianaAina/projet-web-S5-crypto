package com.crypto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "deposit_requests")
public class DepositRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestType type;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime processedAt;
    
    public enum RequestType {
        DEPOSIT, WITHDRAWAL
    }
    
    public enum RequestStatus {
        PENDING, APPROVED, REJECTED
    }
}