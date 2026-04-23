package com.example.catalog.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "created_date")
    private LocalDate createdDate;

    // TODO 1: Add the @OneToMany mapping to the products field below.
    // Use mapped = "category" (this refers to the field name on Product, not the column name).
    // Use FetchType.LAZY -- this is the default but declare it explicitly so the intent is clear.
    // Use cascare = CascadeType.ALL so saving a Category also saves its Products.
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    // Constructors
    public Category() {}
    public Category(String name, String description, LocalDate createdDate) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }

    // Getters and Setters
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }

    public List<Product> getProducts() {return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
