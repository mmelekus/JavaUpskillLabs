package com.example.catalog.controller;

import com.example.catalog.dto.CategoryDto;
import com.example.catalog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO 13: Implement POST /api/v1/categories.
    // Read "name" and "description" from the request body (use Map<String, String>).
    // Call categoryService.create(name, description).
    // Return 201 CREATED with the CategoryDto body.
    // Return 400 BAD REQUEST if an IllegalArgumentException is thrown (duplicate name).
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            String description = body.get("description");
            LocalDate createdDate = LocalDate.now();
            CategoryDto created = categoryService.create(name, description, createdDate);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}