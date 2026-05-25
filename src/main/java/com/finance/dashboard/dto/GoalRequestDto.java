package com.finance.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GoalRequestDto {

    @NotBlank(message = "Goal name is required")
    private String goalName;

    @NotNull(message = "Target amount is required")
    @Positive(message = "Target amount must be a positive decimal value")
    private BigDecimal targetAmount;

    @NotNull(message = "Target date is required")
    @Future(message = "Target date must be a future date")
    private LocalDate targetDate;

    private LocalDate startDate;

    public String getGoalName() { return goalName; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public LocalDate getTargetDate() { return targetDate; }
    public LocalDate getStartDate() { return startDate; }
}