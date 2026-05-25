package com.finance.dashboard.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.finance.dashboard.dto.GoalRequestDto;
import com.finance.dashboard.dto.GoalResponseDto;
import com.finance.dashboard.dto.GoalUpdateDto;
import com.finance.dashboard.entity.CategoryType;
import com.finance.dashboard.entity.Goal;
import com.finance.dashboard.entity.Transaction;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.exception.ResourceNotFoundException;
import com.finance.dashboard.repository.GoalRepository;
import com.finance.dashboard.repository.TransactionRepository;
import com.finance.dashboard.repository.UserRepository;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public GoalService(GoalRepository goalRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public GoalResponseDto createGoal(GoalRequestDto dto, String username) {
        User user = getUser(username);

        LocalDate startDate = dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now();

        Goal goal = new Goal(
                dto.getGoalName(),
                dto.getTargetAmount(),
                dto.getTargetDate(),
                startDate,
                user
        );

        Goal savedGoal = goalRepository.save(goal);
        return mapToResponse(savedGoal, user);
    }

    public List<GoalResponseDto> getAllGoals(String username) {
        User user = getUser(username);
        return goalRepository.findByUser(user)
                .stream()
                .map(goal -> mapToResponse(goal, user))
                .collect(Collectors.toList());
    }

    public GoalResponseDto getGoal(Long id, String username) {
        User user = getUser(username);
        Goal goal = getGoalOwnedByUser(id, user);
        return mapToResponse(goal, user);
    }

    public GoalResponseDto updateGoal(Long id, GoalUpdateDto dto, String username) {
        User user = getUser(username);
        Goal goal = getGoalOwnedByUser(id, user);

        if (dto.getTargetAmount() != null) {
            goal.setTargetAmount(dto.getTargetAmount());
        }
        if (dto.getTargetDate() != null) {
            goal.setTargetDate(dto.getTargetDate());
        }

        Goal updatedGoal = goalRepository.save(goal);
        return mapToResponse(updatedGoal, user);
    }

    public void deleteGoal(Long id, String username) {
        User user = getUser(username);
        Goal goal = getGoalOwnedByUser(id, user);
        goalRepository.delete(goal);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Goal getGoalOwnedByUser(Long id, User user) {
        return goalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
    }

    private GoalResponseDto mapToResponse(Goal goal, User user) {
        BigDecimal currentProgress = calculateProgress(goal, user);
        return new GoalResponseDto(
                goal.getId(),
                goal.getGoalName(),
                goal.getTargetAmount(),
                goal.getTargetDate(),
                goal.getStartDate(),
                currentProgress
        );
    }

    private BigDecimal calculateProgress(Goal goal, User user) {
        List<Transaction> transactions = transactionRepository.findFilteredTransactionsForUser(
                user, goal.getStartDate(), null, null
        );

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            if (t.getCategory().getType() == CategoryType.INCOME) {
                totalIncome = totalIncome.add(t.getAmount());
            } else if (t.getCategory().getType() == CategoryType.EXPENSE) {
                totalExpenses = totalExpenses.add(t.getAmount());
            }
        }

        return totalIncome.subtract(totalExpenses);
    }
}