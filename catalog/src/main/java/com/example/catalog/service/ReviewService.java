package com.example.catalog.service;

import com.example.catalog.model.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final Map<String, Review> store = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(1);

    public ReviewService() {
        // Seed data -- these reviews reference P001 which is seeded in ProductService
        add(new Review(null, "P001", "alice", 5, "Excellent performance"));
        add(new Review(null, "P001", "bob", 4, "Good value for the price"));
        add(new Review(null, "P002", "carol", 3, "Works as expected"));
    }

    public List<Review> findByProductId(String productId) {
        // Convenience method -- delegates to the rated version with no minimum.
        // Keeps any existing callers working without changes.
        return findByProductIdAndRating(productId, 1);
    }

    public Optional<Review> findById(String reviewId) {
        return Optional.ofNullable(store.get(reviewId));
    }

    public Review add(Review review) {
        String id = review.reviewId() != null ? review.reviewId() :
                String.format("R%03d", counter.getAndIncrement());
        Review toStore = new Review(id, review.productId(), review.author(),
                review.rating(), review.comment());
        store.put(id, toStore);
        return toStore;
    }

    // TODO 8: Implement findByProductIdAndRating(String productId, int minRating).
    // Return all reviews for the given product with rating >= minRating.
    // This will be used by the GET /reviews?minRating= endpoint in Task 3.3.
    public List<Review> findByProductIdAndRating(String productId, int minRating) {
        return store.values().stream()
                .filter(r -> r.productId().equals(productId) && r.rating() >= minRating)
                .collect(Collectors.toList());
    }
}
