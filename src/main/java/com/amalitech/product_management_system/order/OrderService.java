package com.amalitech.product_management_system.order;

import com.amalitech.product_management_system.orderItem.OrderItemDto;
import com.amalitech.product_management_system.orderItem.OrderItemService;
import com.amalitech.product_management_system.payment.PaymentService;
import com.amalitech.product_management_system.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.String;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;

    @Transactional
    public String placeOrder(OrderDto orderDto, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        Order order = Order.builder()
                .user(user)
                .build();
        Order savedOrder = orderRepository.save(order);

        List<OrderItemDto> orderItems = orderDto.orderItems();
        BigDecimal totalOrderCost = orderItemService.saveOrderItems(orderItems, savedOrder);
        paymentService.processPayment(totalOrderCost, orderDto.paymentMethod(), savedOrder);

        return "Order placed successfully";
    }
}
