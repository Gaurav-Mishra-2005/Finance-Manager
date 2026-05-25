package com.finance.dashboard.dto;

import com.finance.dashboard.entity.CategoryType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomCategoryRequestDto {

    @NotBlank(message = "Category name is required")
    private String name;

    @NotNull(message = "Category type is required (INCOME or EXPENSE)")
    private CategoryType type;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public CategoryType getType() { return type; }
    public void setType(CategoryType type) { this.type = type; }
}