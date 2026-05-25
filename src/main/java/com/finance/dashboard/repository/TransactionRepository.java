package com.finance.dashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finance.dashboard.entity.Transaction;
import com.finance.dashboard.entity.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.user = :user " +
           "AND (:startDate IS NULL OR t.date >= :startDate) " +
           "AND (:endDate IS NULL OR t.date <= :endDate) " +
           "AND (:categoryId IS NULL OR t.category.id = :categoryId) " +
           "ORDER BY t.date DESC, t.id DESC")
    List<Transaction> findFilteredTransactionsForUser(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("categoryId") Long categoryId
    );
    
    boolean existsByCategory_NameAndUser(String categoryName, User user);
    
    boolean existsByCategory_NameAndCategory_IsCustomFalse(String categoryName);
}