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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dashboard.dto.CategoryDto;
import com.finance.dashboard.dto.CustomCategoryRequestDto;
import com.finance.dashboard.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<CategoryDto>>> getAllCategories(Authentication authentication) {
        String username = authentication.getName(); 
        List<CategoryDto> categories = categoryService.getAllCategoriesForUser(username);
        
        
        Map<String, List<CategoryDto>> response = new HashMap<>();
        response.put("categories", categories);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCustomCategory(
            @Valid @RequestBody CustomCategoryRequestDto dto,
            Authentication authentication) {
        String username = authentication.getName();
        CategoryDto createdCategory = categoryService.createCustomCategory(dto, username);
        
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, String>> deleteCustomCategory(
            @PathVariable String name,
            Authentication authentication) {
        String username = authentication.getName();
        categoryService.deleteCustomCategory(name, username);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Category deleted successfully");
        
        return ResponseEntity.ok(response); 
    }
}