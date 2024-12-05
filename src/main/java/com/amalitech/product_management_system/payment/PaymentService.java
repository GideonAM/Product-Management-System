package com.amalitech.product_management_system.payment;

import com.amalitech.product_management_system.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void processPayment(BigDecimal costOfOrder, String paymentMethod, Order order) {
        String transactionId;
        try {
            transactionId = paymentTransactionId(paymentMethod, costOfOrder);
        } catch (Exception exception) {
            throw new RuntimeException("Payment processing failed");
        }

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(paymentMethod)
                .transactionId(transactionId)
                .paymentStatus(true)
                .paymentAmount(costOfOrder)
                .build();
        paymentRepository.save(payment);
    }

    private String paymentTransactionId(String paymentMethod, BigDecimal totalAmount) {
        // payment processing logic and retrieve transaction id from payment service provider
        return UUID.randomUUID().toString();
    }

}
