package com.example.catalog.service;

import com.example.catalog.exception.ResourceNotFoundException;
import com.example.catalog.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// @Service marks this class as a Spring-managed bean in the service layer.
// It is functionally identical to @Component but communicates intent:
// this class contains business logic, not HTTP handling or data access.
@Service
public class ProductService {
    // In-memory store keyed by product ID.
    // ConcurrentHashMap is used here because Tomcat is multithreaded and may
    // handle concurrent requests. This is not a concern you would manage manually
    // with a real database.
    private final Map<String, Product> store = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    public ProductService() {
        // Seed with sample data so the GET endpoints return something immediately
        save(new Product(null, "Laptop Pro", "ELECTRONICS", 1299.99));
        save(new Product(null, "Wireless Mouse", "ACCESSORIES", 46.99));
        save(new Product(null, "Standing Desk", "FURNITURE", 649.00));
    }

    public ArrayList<Product> findAll() {
        return new ArrayList<Product>(store.values());
    }

    public Optional<Product> findById(String id) {
        // TODO 16: Look up the product in the store.
        // If found, return it directly (not wrapped in Optional -- the exception handles the missing case).
        // If not found, throw new ResourceNotFoundException("Product", id).
        Optional<Product> product = Optional.ofNullable(store.get(id));
        if (product == null) {
            throw new ResourceNotFoundException("Product", id);
        }
        return product;
    }

    public Product save(Product product) {
        // TODO 1: Generate an ID if the product does not already have one.
        // Use the counter field: "P" + counter.getAndIncrement() formatted with String.format
        // to product IDs like p))1, P002, ...
        // Then store the product in the map using the ID as the key.
        // Return the stored product <the one with the generated ID, not the input).
        String id = product.id() != null ? product.id() :
                String.format("P%03d", counter.getAndIncrement());
        Product toStore = new Product(id, product.name(), product.category(), product.price());
        store.put(id, toStore);
        return toStore;
    }

    public boolean delete(String id) {
        // TODO 2: Remove the product from the store.
        // Return true if the product existed and was removed, false if it wass not found.
        return store.remove(id) != null;
    }
}
