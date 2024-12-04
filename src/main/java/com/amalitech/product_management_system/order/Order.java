package com.amalitech.product_management_system.order;

import com.amalitech.product_management_system.common.BaseEntity;
import com.amalitech.product_management_system.orderItem.OrderItem;
import com.amalitech.product_management_system.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.lang.String;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "order_table")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String paymentMethod;

    @OneToMany(mappedBy = "_order")
    @JsonManagedReference
    private List<OrderItem> orderItems;

}
