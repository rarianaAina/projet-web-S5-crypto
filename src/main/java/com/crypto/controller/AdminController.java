package com.crypto.controller;

import com.crypto.model.*;
import com.crypto.repository.CryptocurrencyRepository;
import com.crypto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AnalyticsService analyticsService;
    
    @Autowired
    private CommissionService commissionService;
    
    @Autowired
    private DepositRequestService depositRequestService;

    @Autowired
    private CryptocurrencyRepository cryptocurrencyRepository;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pendingRequests", 
            depositRequestService.getPendingRequests());
        return "admin/dashboard";
    }
    
    @GetMapping("/analytics")
    public String analyticsPage(Model model) {
        model.addAttribute("cryptocurrencies", 
            cryptocurrencyRepository.findAll());
        return "admin/analytics";
    }
    
    @PostMapping("/analytics/calculate")
    @ResponseBody
    public Map<String, BigDecimal> calculateAnalytics(
            @RequestParam List<Long> cryptoIds,
            @RequestParam String analysisType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return analyticsService.calculateStatistics(cryptoIds, analysisType, startDate, endDate);
    }
    
    @GetMapping("/commissions")
    public String commissionsPage(Model model) {
        model.addAttribute("currentCommission", 
            commissionService.getCurrentCommission());
        return "admin/commissions";
    }
    
    @PostMapping("/commissions/update")
    @ResponseBody
    public Commission updateCommissions(
            @RequestParam BigDecimal buyCommission,
            @RequestParam BigDecimal sellCommission) {
        return commissionService.updateCommissions(buyCommission, sellCommission);
    }
    
    @GetMapping("/commissions/analytics")
    @ResponseBody
    public Map<String, BigDecimal> getCommissionAnalytics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam List<Long> cryptoIds) {
        return analyticsService.getCommissionAnalytics(startDate, endDate, cryptoIds);
    }
    
    @GetMapping("/portfolio-summary")
    public String portfolioSummaryPage(
            @RequestParam(required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime maxDate,
            Model model) {
        maxDate = maxDate != null ? maxDate : LocalDateTime.now();
        model.addAttribute("summaries", analyticsService.getPortfolioSummary(maxDate));
        return "admin/portfolio-summary";
    }
    
    @PostMapping("/deposits/{id}/process")
    @ResponseBody
    public ResponseEntity<DepositRequest> processDepositRequest(
            @PathVariable Long id,
            @RequestParam DepositRequest.RequestStatus status) {
        return ResponseEntity.ok(depositRequestService.processRequest(id, status));
    }
}