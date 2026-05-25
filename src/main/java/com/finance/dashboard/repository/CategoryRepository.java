package com.finance.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finance.dashboard.entity.Category;
import com.finance.dashboard.entity.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.isCustom = false OR c.user = :user")
    List<Category> findAllAvailableForUser(@Param("user") User user);

    @Query("SELECT c FROM Category c WHERE c.name = :name AND (c.isCustom = false OR c.user = :user)")
    Optional<Category> findByNameAndAvailableForUser(@Param("name") String name, @Param("user") User user);

    boolean existsByNameAndUser(String name, User user);
    
    boolean existsByNameAndIsCustomFalse(String name);
}