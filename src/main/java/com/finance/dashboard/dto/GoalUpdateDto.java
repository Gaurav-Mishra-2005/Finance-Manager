package com.finance.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;

public class GoalUpdateDto {

    @Positive(message = "Target amount must be a positive decimal value")
    private BigDecimal targetAmount;

    @Future(message = "Target date must be a future date")
    private LocalDate targetDate;

    public BigDecimal getTargetAmount() { return targetAmount; }
    public LocalDate getTargetDate() { return targetDate; }
}