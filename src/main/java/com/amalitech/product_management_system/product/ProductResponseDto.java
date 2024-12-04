package com.amalitech.product_management_system.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponseDto(
        String title,
        String description,
        BigDecimal price,
        Integer quantityInStock,
        String id,
        String productImageUrl,
        String categoryName
) {
}
