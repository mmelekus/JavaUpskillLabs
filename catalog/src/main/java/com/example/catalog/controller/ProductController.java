package com.example.catalog.controller;

import com.example.catalog.config.CatalogProperties;
import com.example.catalog.model.Product;
import com.example.catalog.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

// @RestController combines @Controller and @ResponseBody.
// Every method return value is serialized directly to JSON.
// Without @ResponseBody, Spring MVC would treat the return value as a view name.
@RestController
// @RequestMapping sets the base path for all endpoints in this class.
// /api/v1 is the version prefix -- it is set at the class level so every
// method inherits it. No version logic goes inside a method body.
@RequestMapping("/api/v1/products")
public class ProductController {

    // Constructor injection is the only correct way to inject dependencies in Spring.
    // Spring instantiates this controller and injects the service at startup.
    // The field is final, which means this object cannot be created without it.
    private final ProductService productService;
    private final CatalogProperties properties;

    public ProductController(ProductService productService,
                             CatalogProperties properties) {
        this.productService = productService;
        this.properties = properties;
    }

    // GET /api/v1/products
    // Returns all products. Uses 200 OK implicitly (the default for a non-void return).
    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    // GET /api/v1/products/{id}
    // @PathVariable binds the {id} placeholder in the URL to the method parameter.
    @GetMapping("/{id}")
    public Optional<Product> getById(@PathVariable String id) {
        // TODO 3: Call productService.findById(id).
        // If the product is found, return ResponseEntity.ok(product) -- that produces 200.
        // If it is not found, return ResponseEntity.notFound().build() -- that produces 404.
        // Use the Optional's map/orElse pattern, not an if statement.
        // No Optional handling here -- if the product doesn't exist,
        // ResourceNotFoundException is thrown by the service and caught
        // by GlobalExceptionHandler, which returns 404. The controller
        // only handles the happy path.
        return productService.findById(id);
    }

    // POST /api/v1/products
    // @RequestBody tells Spring to deserialize the JSON request body into a Product object.
    // UriComponentsBuilder is injected by Spring MVC -- it knows the current request's
    // base URL and lets you construct the Location header without hardcoding a host.
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product,
                                          UriComponentsBuilder ucb) {
        // TODO 4: Call productService.save(product) to persist the new product.
        // Build a Location URI pointing to /api/v1/products/{id} using UriComponentsBuilder:
        //   URI location = ucb.path("/api/v1/products/{id}").buildAndExpand(saved.id()).toUri();
        // Return ResponseEntity.created(location).body(saved)
        // This produces a 201 Created response with the Location header set.
        // 201, not 200 -- 201 signals resource creation and carries the new resource's URI.
        Product saved = productService.save(product);
        URI location = ucb.path("/api/v1/products/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    // DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        // TODO 5: Call productService.delete(id).
        // If it returns true (product existed and was deleted), return 204 No Content.
        // If it returns false (product not found), return 404 Not Found.
        // 204 means the operation succeeded but there is no body to return.
        boolean deleted = productService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/info")
    public java.util.Map<String, Object> info() {
        return java.util.Map.of(
                "environment", properties.getEnvironmentLabel(),
                "defaultPageSize", properties.getPageSizeDefault(),
                "maxPageSize", properties.getPageSizeMax()
        );
    }

    // @ModelAttribute methods without a return value are executed before every
    // request-handling method in the same controller class.
    // This is the correct place to add headers that should appear on every response.
    @ModelAttribute
    public void addDeprecationHeaders(HttpServletResponse response) {
        // TODO 19: Add these three headers to every v1 response:
        //   Deprecation: true
        //   Sunset: Sat, 01 Nov 2025 00:00:00 GMT
        //   Link: </api/v2/products>; rel="successor-version"
        response.setHeader("Deprecation", "true");
        response.setHeader("Sunset", "Sat, 01 Nov 2026 00:00:00 GMT");
        response.setHeader("Link", "</api/v2/products>; rel=\"successor-version\"");
    }
}