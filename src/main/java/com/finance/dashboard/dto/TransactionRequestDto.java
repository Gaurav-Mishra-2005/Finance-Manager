package com.finance.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public class TransactionRequestDto {

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive decimal value")
    private BigDecimal amount;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be a future date")
    private LocalDate date;

    @NotBlank(message = "Category name is required")
    private String category;

    private String description;

    // Getters and Setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}