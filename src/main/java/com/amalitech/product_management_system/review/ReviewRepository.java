package com.amalitech.product_management_system.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("""
            SELECT rv
            FROM Review rv
            WHERE rv.product.id = :productId
            """)
    Page<Review> findReviewsByProductId(Pageable pageable, String productId);
}
