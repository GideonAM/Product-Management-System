package com.amalitech.product_management_system.review;

import jakarta.validation.constraints.*;

public record ReviewDto(
        @NotBlank(message = "Review message is required")
        String reviewMessage,
        @DecimalMax(value = "5", message = "Rating must be between 1 to 5")
        @DecimalMin(value = "1", message = "Rating must be from 1 to 5")
        int rating
) {
}
