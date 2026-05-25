package com.finance.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.finance.dashboard.entity.CategoryType;

public class TransactionResponseDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private String category;
    private String description;
    private CategoryType type;

    public TransactionResponseDto(Long id, BigDecimal amount, LocalDate date, String category, String description, CategoryType type) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
        this.type = type;
    }

    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public CategoryType getType() { return type; }

    public void setId(Long id) { this.id = id; }    
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setType(CategoryType type) { this.type = type; }

}
