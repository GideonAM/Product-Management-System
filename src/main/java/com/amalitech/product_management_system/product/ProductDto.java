package com.amalitech.product_management_system.product;

import com.amalitech.product_management_system.category.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDto(
        @NotBlank(message = "Product title is required")
        String title,
        @NotBlank(message = "Product description is required")
        String description,
        @PositiveOrZero(message = "Product price should be positive")
        BigDecimal price,
        @Positive(message = "Product quantity should be above zero")
        Integer quantityInStock,
        @NotBlank(message = "Product category id is required")
        String categoryId
) {
}
