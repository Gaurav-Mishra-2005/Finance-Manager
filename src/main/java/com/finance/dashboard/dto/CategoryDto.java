package com.finance.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finance.dashboard.entity.CategoryType;

public class CategoryDto {
    private Long id;
    private String name;
    private CategoryType type;
    
    @JsonProperty("isCustom")
    private boolean isCustom;

    public CategoryDto(Long id, String name, CategoryType type, boolean isCustom) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.isCustom = isCustom;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public CategoryType getType() { return type; }
    public boolean getIsCustom() { return isCustom; }

    @JsonProperty("custom")
    public boolean getCustom() { return isCustom; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(CategoryType type) { this.type = type; }
    public void setCustom(boolean isCustom) { this.isCustom = isCustom; }


}