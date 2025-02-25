package com.crypto.repository;

import com.crypto.model.Commission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommissionRepository extends JpaRepository<Commission, Long> {
    Optional<Commission> findFirstByActiveOrderByCreatedAtDesc(boolean active);
}