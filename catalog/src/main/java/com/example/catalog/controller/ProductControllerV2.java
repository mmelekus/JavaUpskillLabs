package com.example.catalog.controller;

import com.example.catalog.model.ProductV2;
import com.example.catalog.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

// The version prefix is changed to v2 at the class level.
// There is no version-branching logic inside any method.
// The version is an address, not a condition.
@RestController
@RequestMapping("/api/v2/products")
public class ProductControllerV2 {

    private final ProductService productService;

    public ProductControllerV2(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductV2> getAll() {
        // TODO 17: Call productService.findAll() to get the list of v1 Products.
        // Map each Product to a ProductV2 using a Stream:
        //   - copy id, name, and price directly
        //   - map "category" to "productCategory"
        //   - set lastUpdated to Instant.now() (in a real service this would
        //     come from the database, but for this exercise a fixed value is fine)
        return productService.findAll().stream()
                .map(p -> new ProductV2(p.id(), p.name(), p.category(),
                        p.price(), Instant.now()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductV2 getById(@PathVariable String id) {
        // TODO 18: Call productService.findById(id) -- it throws ResourceNotFoundException
        // if not found, so no null-checking is needed.
        // Map the returned Product to ProductV2 the same way as above.
        var p = productService.findById(id);
        return new ProductV2(p.id(), p.name(), p.category(), p.price(), Instant.now());
    }
}