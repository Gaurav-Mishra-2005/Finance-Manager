package com.finance.dashboard.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.finance.dashboard.dto.TransactionRequestDto;
import com.finance.dashboard.dto.TransactionResponseDto;
import com.finance.dashboard.dto.TransactionUpdateDto;
import com.finance.dashboard.entity.Category;
import com.finance.dashboard.entity.Transaction;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.exception.ResourceNotFoundException;
import com.finance.dashboard.repository.CategoryRepository;
import com.finance.dashboard.repository.TransactionRepository;
import com.finance.dashboard.repository.UserRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public TransactionResponseDto createTransaction(TransactionRequestDto dto, String username) {
        User user = getUser(username);
        Category category = getValidCategoryForUser(dto.getCategory(), user);

        Transaction transaction = new Transaction(
                dto.getAmount(),
                dto.getDate(),
                dto.getDescription(),
                category,
                user
        );

        Transaction saved = transactionRepository.save(transaction);
        return mapToResponse(saved);
    }

    public List<TransactionResponseDto> getTransactions(String username, LocalDate startDate, LocalDate endDate, Long categoryId) {
        User user = getUser(username);
        
        List<Transaction> transactions = transactionRepository.findFilteredTransactionsForUser(user, startDate, endDate, categoryId);
        
        return transactions.stream()
                .filter(t -> startDate == null || !t.getDate().isBefore(startDate))
                .filter(t -> endDate == null || !t.getDate().isAfter(endDate))
                .filter(t -> categoryId == null || t.getCategory().getId().equals(categoryId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponseDto updateTransaction(Long id, TransactionUpdateDto dto, String username) {
        Transaction transaction = getTransactionOwnedByUser(id, username);

        if (dto.getAmount() != null) {
            transaction.setAmount(dto.getAmount());
        }
        if (dto.getDescription() != null) {
            transaction.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null) {
            Category newCategory = getValidCategoryForUser(dto.getCategory(), transaction.getUser());
            transaction.setCategory(newCategory);
        }

        Transaction updated = transactionRepository.save(transaction);
        return mapToResponse(updated);
    }

    public void deleteTransaction(Long id, String username) {
        Transaction transaction = getTransactionOwnedByUser(id, username);
        transactionRepository.delete(transaction);
    }


    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Category getValidCategoryForUser(String categoryName, User user) {
        return categoryRepository.findByNameAndAvailableForUser(categoryName, user)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category: " + categoryName + " is not accessible."));
    }

    private Transaction getTransactionOwnedByUser(Long transactionId, String username) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (!transaction.getUser().getUsername().equals(username)) {

            throw new ResourceNotFoundException("Transaction not found");
        }
        return transaction;
    }

    private TransactionResponseDto mapToResponse(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getCategory().getName(),
                transaction.getDescription(),
                transaction.getCategory().getType()
        );
    }
}