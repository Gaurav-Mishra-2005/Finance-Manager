package com.finance.dashboard.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.finance.dashboard.dto.MonthlyReportDto;
import com.finance.dashboard.dto.YearlyReportDto;
import com.finance.dashboard.entity.CategoryType;
import com.finance.dashboard.entity.Transaction;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.exception.ResourceNotFoundException;
import com.finance.dashboard.repository.TransactionRepository;
import com.finance.dashboard.repository.UserRepository;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public ReportService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public MonthlyReportDto generateMonthlyReport(int year, int month, String username) {
        User user = getUser(username);
        
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Transaction> transactions = transactionRepository.findFilteredTransactionsForUser(user, startDate, endDate, null);

        Map<String, BigDecimal> totalIncome = new HashMap<>();
        Map<String, BigDecimal> totalExpenses = new HashMap<>();
        BigDecimal netSavings = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            String categoryName = t.getCategory().getName();
            BigDecimal amount = t.getAmount();

            if (t.getCategory().getType() == CategoryType.INCOME) {
                totalIncome.merge(categoryName, amount, BigDecimal::add);
                netSavings = netSavings.add(amount);
            } else {
                totalExpenses.merge(categoryName, amount, BigDecimal::add);
                netSavings = netSavings.subtract(amount);
            }
        }

        return new MonthlyReportDto(month, year, totalIncome, totalExpenses, netSavings);
    }

    public YearlyReportDto generateYearlyReport(int year, String username) {
        User user = getUser(username);
        
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<Transaction> transactions = transactionRepository.findFilteredTransactionsForUser(user, startDate, endDate, null);

        Map<String, BigDecimal> totalIncome = new HashMap<>();
        Map<String, BigDecimal> totalExpenses = new HashMap<>();
        BigDecimal netSavings = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            String categoryName = t.getCategory().getName();
            BigDecimal amount = t.getAmount();

            if (t.getCategory().getType() == CategoryType.INCOME) {
                totalIncome.merge(categoryName, amount, BigDecimal::add);
                netSavings = netSavings.add(amount);
            } else {
                totalExpenses.merge(categoryName, amount, BigDecimal::add);
                netSavings = netSavings.subtract(amount);
            }
        }

        return new YearlyReportDto(year, totalIncome, totalExpenses, netSavings);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}