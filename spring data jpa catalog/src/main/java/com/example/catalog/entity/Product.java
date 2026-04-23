package com.example.catalog.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    // TODO 2: Add the @ManyToOne mapping for the category field.
    // Use fetch = FetchType.LAZY
    // Add @JoinColumn(name = "category_id", nullable = false).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "sku", unique = true, nullable = false, length = 50)
    private String sku;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_qty", nullable = false)
    private Integer stockQty = 0;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    // Constructors
    public Product() {}

    public Product(Category category, String name, String sku, BigDecimal price) {
        this.category = category;
        this.name = name;
        this.sku = sku;
        this.price = price;
    }

    // Getters and Setters
    public Long getProductId() { return  productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPrice() { return this.price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStockQty() { return stockQty; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }

    public Boolean getActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
