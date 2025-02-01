package com.crypto.controller;

import com.crypto.model.*;
import com.crypto.repository.*;
import com.crypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/history")
public class TransactionHistoryController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewHistory(
            HttpSession session,
            @RequestParam(required = false) List<Long> cryptoIds,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Model model) {

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getUserById(userId);

        List<Transaction> transactions;
        if (cryptoIds != null && startDate != null && endDate != null) {
            transactions = transactionRepository.findByDateRangeAndCryptos(
                    startDate, endDate, cryptoIds);
        } else {
            transactions = transactionRepository.findByUserOrderByTimestampDesc(user);
        }

        model.addAttribute("transactions", transactions);
        return "history";
    }
}