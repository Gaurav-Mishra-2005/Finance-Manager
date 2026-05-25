package com.finance.dashboard.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;

public class TransactionUpdateDto {

    @Positive(message = "Amount must be a positive decimal value")
    private BigDecimal amount;

    private String category;
    private String description;

    // Getters and Setters
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}