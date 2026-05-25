package com.finance.dashboard.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class GoalResponseDto {
    private Long id;
    private String goalName;
    private BigDecimal targetAmount;
    private LocalDate targetDate;
    private LocalDate startDate;
    private BigDecimal currentProgress;
    private BigDecimal remainingAmount;
    private double progressPercentage;

    public GoalResponseDto(Long id, String goalName, BigDecimal targetAmount, LocalDate targetDate, 
                           LocalDate startDate, BigDecimal currentProgress) {
        this.id = id;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.targetDate = targetDate;
        this.startDate = startDate;
        
        this.currentProgress = currentProgress.max(BigDecimal.ZERO);
        
        this.remainingAmount = this.targetAmount.subtract(this.currentProgress).max(BigDecimal.ZERO);
        
        if (this.targetAmount.compareTo(BigDecimal.ZERO) > 0) {
        BigDecimal calc = this.currentProgress
                .divide(this.targetAmount, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
        
        this.progressPercentage = Math.min(calc.doubleValue(), 100.0);
    } else {
        this.progressPercentage = 0.0;
    }
    }

    public Long getId() { return id; }
    public String getGoalName() { return goalName; }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public LocalDate getTargetDate() { return targetDate; }
    public LocalDate getStartDate() { return startDate; }
    public BigDecimal getCurrentProgress() { return currentProgress; }
    public double getProgressPercentage() { return progressPercentage; }
    public BigDecimal getRemainingAmount() { return remainingAmount; }

    public void setId(Long id) { this.id = id; }
    public void setGoalName(String goalName) { this.goalName = goalName; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setTargetDate(LocalDate targetDate) { this.targetDate = targetDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setCurrentProgress(BigDecimal currentProgress) { this.currentProgress = currentProgress; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }
    public void setRemainingAmount(BigDecimal remainingAmount) { this.remainingAmount = remainingAmount;}
    
}