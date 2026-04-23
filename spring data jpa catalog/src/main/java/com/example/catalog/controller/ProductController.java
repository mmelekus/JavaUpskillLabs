package com.example.catalog.controller;

import com.example.catalog.dto.ProductCreateRequest;
import com.example.catalog.dto.ProductDto;
import com.example.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAll() {
        return productService.findAllActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductCreateRequest request) {
        try {
            ProductDto created = productService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // TODO 12: Implement the PATCH /api/v1/products/{id}/stock endpoint.
    // It should read a "delta" integer from the request body (use Map<String, Integer>).
    // Call productService.adjustStock(id, delta) and return 200 OK with the updated DTO.
    // Return 404 if the product is not found.
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDto> adjustStock(@PathVariable Long id,
                                                  @RequestBody Map<String, Integer> body) {
        try {
            int delta = body.getOrDefault("delta", 0);
            return ResponseEntity.ok(productService.adjustStock(id, delta));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        try {
            productService.deactivate(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}