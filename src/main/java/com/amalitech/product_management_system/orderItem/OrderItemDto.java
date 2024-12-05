package com.amalitech.product_management_system.orderItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record OrderItemDto(
        @NotBlank(message = "Product id is required")
        String productId,
        @Positive(message = "Product quantity should be above zero")
        Integer quantity
) {
}
