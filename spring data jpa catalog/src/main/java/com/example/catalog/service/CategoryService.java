package com.example.catalog.service;

import com.example.catalog.dto.CategoryDto;
import com.example.catalog.entity.Category;
import com.example.catalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryRepository.findAllOrderedByName()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + id));
    }

    @Transactional
    public CategoryDto create(String name, String description, LocalDate createdDate) {
        // TODO 11: Check whether a category with this name already exists
        // using categoryRepository.findByName(name).
        // If present, throw new IllegalArgumentException("Category already exists: " + name).
        // Otherwise construct a new Category entity, call categoryRepository.save(), and return the DTO.
        categoryRepository.findByName(name).ifPresent(existing -> {
            throw new IllegalArgumentException("Category already exists: " + name);
        });

        Category category = new Category(name, description, createdDate);
        Category saved = categoryRepository.save(category);
        return toDto(saved);
    }

    private CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }
}