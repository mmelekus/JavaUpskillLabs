package com.example.catalog.repository;

import com.example.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived query: Spring Data generates WHERE sku = :sku from the method name.
    Optional<Product> findBySku(String sku);

    // Derived query with two conditions: WHERE active = :active AND category.
    List<Product> findByActiveAndCategoryCategoryId(Boolean active, Long categoryId);

    // Derived query with a price range: WHERE price BETWEEN :min AND :max.
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    // TODO 5: Add a JPQL query that fetches products and eagerly joins their category
    // in a single SQL statement.  This prevents the N+1 problem when you later need
    // category data alongside the product.
    //
    // JPQL operates on Java class and field names, not table and column names.
    // The query is:
    //   SELECT p FROM Product p JOIN FETCH p.category WHERE p.active = true
    //
    // Use @Query("...") without nativeQuery = true.
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.active = true")
    List<Product> findAllActiveWithCategory();

    // TODO 6: Add a native Oracle SQL query that returns the top 5 most expensive
    // active products.  Use FETCH FIRST syntax (Oracle 12c+).
    // Mark it with @Query(value = "...", nativeQuery = true).
    @Query(value = """
        SELECT * FROM products
        WHERE active = 1
        ORDER BY price DESC
        FETCH FIRST 5 ROWS ONLY
        """, nativeQuery = true)
    List<Product> findTop5MostExpensive();
}
