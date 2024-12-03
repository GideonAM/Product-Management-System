package com.amalitech.product_management_system.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
        @NotBlank(message = "Category name is required")
        String categoryName,
        String id
) {
}
