package com.finance.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finance.dashboard.entity.Goal;
import com.finance.dashboard.entity.User;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    
    List<Goal> findByUser(User user);
    
    Optional<Goal> findByIdAndUser(Long id, User user);
}