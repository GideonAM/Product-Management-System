package com.amalitech.product_management_system.review;

import com.amalitech.product_management_system.common.PageResponse;
import com.amalitech.product_management_system.product.Product;
import com.amalitech.product_management_system.product.ProductRepository;
import com.amalitech.product_management_system.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ReviewDto createReview(@Valid ReviewDto reviewDto, String productId, Authentication connectedUser) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product to review not found"));
        User user = (User) connectedUser.getPrincipal();

        Review review = Review.builder()
                .reviewMessage(reviewDto.reviewMessage())
                .rating(reviewDto.rating())
                .user(user)
                .product(product)
                .build();

        reviewRepository.save(review);
        return reviewDto;
    }

    public PageResponse<ReviewDto> getAllProductReview(String productId, int page, int size) {
        productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Invalid product id"));
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewRepository.findReviewsByProductId(pageable, productId);

        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewDto(review.getReviewMessage(), review.getRating()))
                .toList();

        return new PageResponse<>(
                reviewDtos,
                reviews.isFirst(),
                reviews.isLast(),
                reviews.getTotalPages(),
                reviews.getTotalElements()
        );
    }
}
