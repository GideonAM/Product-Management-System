package com.amalitech.product_management_system.orderItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Builder
@Validated
public record OrderItemDto(
        @NotBlank(message = "Product id is require")
        String productId,
        @Positive(message = "Product price should be above zero")
        BigDecimal productPrice,
        @Positive(message = "Product quantity should be above zero")
        Integer quantity
) {
}
