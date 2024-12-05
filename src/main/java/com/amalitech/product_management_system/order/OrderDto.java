package com.amalitech.product_management_system.order;

import com.amalitech.product_management_system.orderItem.OrderItemDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderDto(
        @NotBlank(message = "Payment method is required")
        String paymentMethod,
        List<@Valid OrderItemDto> orderItems
) {
}
