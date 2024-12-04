package com.amalitech.product_management_system.orderItem;

import com.amalitech.product_management_system.common.BaseEntity;
import com.amalitech.product_management_system.order.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderItem extends BaseEntity {
    
    @Column(nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private BigDecimal productPrice;
    
    @Column(nullable = false)
    private Integer productQuantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order _order;
    
}
