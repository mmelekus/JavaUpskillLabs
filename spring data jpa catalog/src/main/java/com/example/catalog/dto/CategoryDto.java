package com.example.catalog.dto;

public class CategoryDto {

    private Long categoryId;
    private String name;
    private String description;

    // Constructor used by the service layer to map from the entity
    public CategoryDto(Long categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public Long getCategoryId() { return categoryId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}