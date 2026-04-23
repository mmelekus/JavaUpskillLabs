package com.example.catalog.model;

// Review is a sub-resource: its identity is only meaningful in the context
// of a parent Product. A review without a product has no domain meaning.
// This is a the correct signal that a hierarchical URL (/[rpdicts/{id}/reviews)
// is appropriate here.
public record Review(
        String reviewId,
        String productId,  // the parent's identity is part of the review's state
        String author,
        int rating,        // 1 to 5
        String comment
) {}
