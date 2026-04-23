package com.example.catalog.service;

import com.example.catalog.dto.ProductCreateRequest;
import com.example.catalog.dto.ProductDto;
import com.example.catalog.entity.Category;
import com.example.catalog.entity.Product;
import com.example.catalog.repository.CategoryRepository;
import com.example.catalog.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Read-only transaction: JPA skips dirty checking, Oracle can optimise locking
    @Transactional(readOnly = true)
    public List<ProductDto> findAllActive() {
        // TODO 7: Call productRepository.findAllActiveWithCategory() to fetch active
        // products with their category in a single JOIN FETCH query.
        // Map each Product to a ProductDto using the private toDto() method below.
        // Return the mapped list.
        return productRepository.findAllActiveWithCategory()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        // TODO 8: Use productRepository.findById(id).
        // If the Optional is empty, throw new NoSuchElementException("Product not found: " + id).
        // Otherwise map and return the ProductDto.
        return productRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + id));
    }

    @Transactional
    public ProductDto create(ProductCreateRequest request) {
        // TODO 9: Load the Category from categoryRepository.findById(request.getCategoryId()).
        // If absent, throw NoSuchElementException("Category not found: " + request.getCategoryId()).
        //
        // Construct a new Product entity:
        //   product.setCategory(category);
        //   product.setName(request.getName());
        //   product.setSku(request.getSku());
        //   product.setPrice(request.getPrice());
        //   product.setStockQty(request.getStockQty());
        //
        // Call productRepository.save(product) and return the mapped DTO.
        //
        // Note: the product is new (not yet managed), so save() is required here.
        // After the first save(), the entity becomes managed for the rest of this transaction.
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Category not found: " + request.getCategoryId()));

        Product product = new Product();
        product.setCategory(category);
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setStockQty(request.getStockQty());

        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    @Transactional
    public ProductDto adjustStock(Long id, int delta) {
        // TODO 10: Load the product by ID (throw NoSuchElementException if absent).
        //
        // Update the stock quantity:
        //   product.setStockQty(product.getStockQty() + delta);
        //
        // DO NOT call productRepository.save().
        //
        // Because the product is now a MANAGED entity inside this @Transactional method,
        // Hibernate's dirty checking will detect the field change automatically and issue
        // an UPDATE when the transaction commits. You do not need an explicit save() call.
        //
        // After completing the TODO, add a System.out.println before the return:
        //   System.out.println("No explicit save() called -- dirty checking handles the UPDATE");
        // Run the endpoint and verify the UPDATE appears in the console log anyway.
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + id));

        product.setStockQty(product.getStockQty() + delta);

        System.out.println("No explicit save() called -- dirty checking handles the UPDATE");
        return toDto(product);
    }

    @Transactional
    public void deactivate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found: " + id));
        product.setActive(false);
        // Again: no save() needed. Dirty checking will issue the UPDATE.
    }

    // Private mapping helper. Accesses product.getCategory().getName(),
    // which is safe here because this method is always called inside
    // an open transaction (the caller's @Transactional boundary).
    private ProductDto toDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getSku(),
                product.getPrice(),
                product.getStockQty(),
                product.getActive(),
                product.getCategory().getName()   // triggers lazy load if not already fetched
        );
    }
}