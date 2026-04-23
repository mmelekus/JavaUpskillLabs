package com.example.catalog.repository;

import com.example.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // TODO 3: Add a derived query method that finds a Category by its name field.
    // The method signature should be:
    //   Optional<Category> findByName(String name);
    // Spring Data will parse "findByName" and generaate:
    //   SELECT c FROM Category c WHERE c.name = :name
    Optional<Category> findByName(String name);

    // TODO 4: Add a native query that returns all categories ordered by name.
    // Use @Query(value = "...", nativeQuery = true).
    // Native queries use Oracle SQL syntax and column names, not Java field names.
    @Query(value = "SELECT * FROM categories ORDER BY name", nativeQuery = true)
    List<Category> findAllOrderedByName();
}
