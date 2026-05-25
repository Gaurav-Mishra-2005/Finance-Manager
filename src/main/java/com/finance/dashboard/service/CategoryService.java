package com.finance.dashboard.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.finance.dashboard.dto.CategoryDto;
import com.finance.dashboard.dto.CustomCategoryRequestDto;
import com.finance.dashboard.entity.Category;
import com.finance.dashboard.entity.CategoryType;
import com.finance.dashboard.entity.User;
import com.finance.dashboard.exception.ConflictException;
import com.finance.dashboard.repository.CategoryRepository;
import com.finance.dashboard.repository.TransactionRepository;
import com.finance.dashboard.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostConstruct
    public void initDefaultCategories() {
        seedCategoryIfNotExists("Salary", CategoryType.INCOME);
        seedCategoryIfNotExists("Food", CategoryType.EXPENSE);
        seedCategoryIfNotExists("Rent", CategoryType.EXPENSE);
        seedCategoryIfNotExists("Transportation", CategoryType.EXPENSE);
        seedCategoryIfNotExists("Entertainment", CategoryType.EXPENSE);
        seedCategoryIfNotExists("Healthcare", CategoryType.EXPENSE);
        seedCategoryIfNotExists("Utilities", CategoryType.EXPENSE);
    }

    private void seedCategoryIfNotExists(String name, CategoryType type) {
        if (!categoryRepository.existsByNameAndIsCustomFalse(name)) {
            Category category = new Category(name, type, false, null);
            categoryRepository.save(category);
        }
    }

    public List<CategoryDto> getAllCategoriesForUser(String username) {
        User user = getUser(username);
        return categoryRepository.findAllAvailableForUser(user)
                .stream()
                .map(c -> new CategoryDto(c.getName(), c.getType(), c.isCustom()))
                .collect(Collectors.toList());
    }

    public CategoryDto createCustomCategory(CustomCategoryRequestDto dto, String username) {
        User user = getUser(username);

        if (categoryRepository.findByNameAndAvailableForUser(dto.getName(), user).isPresent()) {
            throw new RuntimeException("Category already exists: " + dto.getName()); 
        }

        Category category = new Category(dto.getName(), dto.getType(), true, user);
        Category saved = categoryRepository.save(category);

        return new CategoryDto(saved.getName(), saved.getType(), saved.isCustom());
    }

    public void deleteCustomCategory(String name, String username) {
        User user = getUser(username);
        
        Category category = categoryRepository.findByNameAndAvailableForUser(name, user)
                .orElseThrow(() -> new RuntimeException("Category not found: " + name)); 

        if (!category.isCustom()) {
            throw new com.finance.dashboard.exception.ForbiddenException("Cannot delete default categories"); 
        }
        if (transactionRepository.existsByCategory_NameAndUser(name, user)) {
            throw new ConflictException("Cannot delete category as it is currently referenced by one or more transactions");
        }

        categoryRepository.delete(category);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}