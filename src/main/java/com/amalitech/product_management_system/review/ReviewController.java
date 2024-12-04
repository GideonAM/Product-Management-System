package com.amalitech.product_management_system.review;

import com.amalitech.product_management_system.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products/{product-id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @Valid @RequestBody ReviewDto reviewDto,
            @PathVariable("product-id") String productId,
            Authentication connectedUser) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(reviewDto, productId, connectedUser));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ReviewDto>> getAllProductReview(
            @PathVariable("product-id") String productId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size) {

        return ResponseEntity.ok(reviewService.getAllProductReview(productId, page, size));
    }

}
