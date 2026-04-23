package com.example.catalog.model;

// A record is Java's concise immutable data carrier.
// The parenthesized list is both the field declaration and the constructor.
// Accessors use the field name directly: product.id(), product.name() -- no "get" prefix.
public record Product(
        String id,
        String name,
        String category,
        double price
) {}
