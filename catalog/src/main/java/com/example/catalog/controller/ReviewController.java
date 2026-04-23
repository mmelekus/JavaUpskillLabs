package com.example.catalog.controller;

import com.example.catalog.model.Review;
import com.example.catalog.service.ProductService;
import com.example.catalog.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
// The base path encodes the ownership relationship:
// a review is a child of a product, so the product's ID appears in the path.
// This is the correct design when a child resource's identity is only
// meaningful in the context of its parent.
@RequestMapping("/api/v1/products/{productId}/reviews")
public class ReviewController {

    private final ProductService productService;
    private final ReviewService reviewService;

    public ReviewController(ProductService productService, ReviewService reviewService) {
        this.productService = productService;
        this.reviewService = reviewService;
    }

    // GET /api/v1/products/{productId}/reviews
    // GET /api/v1/products/{productId}/reviews?minRating=4
    //
    // Both URLs are handled by this single method.
    // minRating is a query parameter -- it refines the collection, it does not
    // identify a different resource. The URL /reviews?minRating=4 and /reviews
    // refer to the same collection, shaped differently.
    // When minRating is absent, defaultValue = "1" means all reviews are returned.
    //
    // First verifies the parent product exists -- a 404 here is semantically correct
    // because the sub-resource collection itself does not exist without the parent.
    @GetMapping
    public ResponseEntity<List<Review>> getReviews(
            @PathVariable String productId,
            // required = false means the parameter is optional.
            // defaultValue provides the fallback so the method always receives a value.
            @RequestParam(required = false, defaultValue = "1") int minRating) {

        // TODO 9: Check that the product with productId exists (use productService.findById).
        // If it does not exist, return 404.
        // If it does, call reviewService.findByProductIdAndRating(productId, minRating)
        // and return 200 with the result.
        // Because minRating defaults to 1, this returns all reviews when no filter is supplied.
        return productService.findById(productId)
                .map(p -> ResponseEntity.ok(
                        reviewService.findByProductIdAndRating(productId, minRating)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/products/{productId}/reviews
    @PostMapping
    public ResponseEntity<Review> addReview(@PathVariable String productId,
                                            @RequestBody Review review,
                                            UriComponentsBuilder ucb) {
        // TODO 10: Verify the product exists. Return 404 if not.
        // If the product exists, call reviewService.add(), binding the productId from the
        // path variable into the Review (not the request body -- the client should not
        // set the productId; it comes from the URL).
        // Return 201 Created with a Location header:
        //   /api/v1/products/{productId}/reviews/{reviewId}
        if (productService.findById(productId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Review toSave = new Review(null, productId, review.author(),
                review.rating(), review.comment());
        Review saved = reviewService.add(toSave);
        URI location = ucb.path("/api/v1/products/{productId}/reviews/{reviewId}")
                .buildAndExpand(productId, saved.reviewId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }
}