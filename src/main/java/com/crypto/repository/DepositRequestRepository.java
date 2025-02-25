package com.crypto.repository;

import com.crypto.model.DepositRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepositRequestRepository extends JpaRepository<DepositRequest, Long> {
    List<DepositRequest> findByStatusOrderByCreatedAtDesc(DepositRequest.RequestStatus status);
}