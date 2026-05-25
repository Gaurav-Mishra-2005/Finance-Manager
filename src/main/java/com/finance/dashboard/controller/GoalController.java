package com.finance.dashboard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.dto.GoalRequestDto;
import com.finance.dashboard.dto.GoalResponseDto;
import com.finance.dashboard.dto.GoalUpdateDto;
import com.finance.dashboard.service.GoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<GoalResponseDto> createGoal(
            @Valid @RequestBody GoalRequestDto dto,
            Authentication authentication) {
        String username = authentication.getName();
        GoalResponseDto createdGoal = goalService.createGoal(dto, username);
        
        return new ResponseEntity<>(createdGoal, HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<Map<String, List<GoalResponseDto>>> getAllGoals(Authentication authentication) {
        String username = authentication.getName();
        List<GoalResponseDto> goals = goalService.getAllGoals(username);
        
        Map<String, List<GoalResponseDto>> response = new HashMap<>();
        response.put("goals", goals);
        
        return ResponseEntity.ok(response); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDto> getGoal(
            @PathVariable Long id,
            Authentication authentication) {
        String username = authentication.getName();
        GoalResponseDto goal = goalService.getGoal(id, username);
        
        return ResponseEntity.ok(goal); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDto> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody GoalUpdateDto dto,
            Authentication authentication) {
        String username = authentication.getName();
        GoalResponseDto updatedGoal = goalService.updateGoal(id, dto, username);
        
        return ResponseEntity.ok(updatedGoal); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteGoal(
            @PathVariable Long id,
            Authentication authentication) {
        String username = authentication.getName();
        goalService.deleteGoal(id, username);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Goal deleted successfully");
        
        return ResponseEntity.ok(response); 
    }
}