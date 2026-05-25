package com.finance.dashboard.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.dto.TransactionRequestDto;
import com.finance.dashboard.dto.TransactionResponseDto;
import com.finance.dashboard.dto.TransactionUpdateDto;
import com.finance.dashboard.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(
            @Valid @RequestBody TransactionRequestDto dto,
            Authentication authentication) {
        String username = authentication.getName();
        TransactionResponseDto createdTransaction = transactionService.createTransaction(dto, username);
        
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<Map<String, List<TransactionResponseDto>>> getTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String category, 
            Authentication authentication) {
        
        String username = authentication.getName();
        List<TransactionResponseDto> transactions = transactionService.getTransactions(username, startDate, endDate, categoryId, category);
        
        Map<String, List<TransactionResponseDto>> response = new HashMap<>();
        response.put("transactions", transactions);
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionUpdateDto dto,
            Authentication authentication) {
        
        String username = authentication.getName();
        TransactionResponseDto updatedTransaction = transactionService.updateTransaction(id, dto, username);
        
        return ResponseEntity.ok(updatedTransaction); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTransaction(
            @PathVariable Long id,
            Authentication authentication) {
        
        String username = authentication.getName();
        transactionService.deleteTransaction(id, username);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Transaction deleted successfully");
        
        return ResponseEntity.ok(response); 
    }
}