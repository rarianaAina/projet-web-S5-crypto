package com.crypto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PostgreSQL AUTO_INCREMENT
    private Long id;

    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String role;
    
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
}