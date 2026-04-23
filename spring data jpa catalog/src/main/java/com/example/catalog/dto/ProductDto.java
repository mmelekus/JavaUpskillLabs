package com.example.catalog.dto;

import java.math.BigDecimal;

public class ProductDto {

    private Long productId;
    private String name;
    private String sku;
    private BigDecimal price;
    private Integer stockQty;
    private Boolean active;
    private String categoryName;  // flattened from the Category association

    public ProductDto(Long productId, String name, String sku,
                      BigDecimal price, Integer stockQty,
                      Boolean active, String categoryName) {
        this.productId = productId;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.stockQty = stockQty;
        this.active = active;
        this.categoryName = categoryName;
    }

    public Long getProductId() { return productId; }
    public String getName() { return name; }
    public String getSku() { return sku; }
    public BigDecimal getPrice() { return price; }
    public Integer getStockQty() { return stockQty; }
    public Boolean getActive() { return active; }
    public String getCategoryName() { return categoryName; }
}