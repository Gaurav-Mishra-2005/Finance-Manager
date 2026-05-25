package com.finance.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.dashboard.entity.CategoryType;

public class CategoryDto {
    private String name;
    private CategoryType type;
    
    @JsonProperty("isCustom")
    private boolean isCustom;

    public CategoryDto(String name, CategoryType type, boolean isCustom) {
        this.name = name;
        this.type = type;
        this.isCustom = isCustom;
    }

    public String getName() { return name; }
    public CategoryType getType() { return type; }
    @JsonProperty("custom")
    public boolean getCustom() { 
        return isCustom; 
    }

    public void setName(String name) { this.name = name; }
    public void setType(CategoryType type) { this.type = type; }
    public void setIsCustom(boolean isCustom) { this.isCustom = isCustom; } 
}