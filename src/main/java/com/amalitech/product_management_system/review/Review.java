package com.amalitech.product_management_system.review;

import com.amalitech.product_management_system.common.BaseEntity;
import com.amalitech.product_management_system.product.Product;
import com.amalitech.product_management_system.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Review extends BaseEntity {
    @Column(nullable = false)
    private int rating;
    @Column(nullable = false)
    private String reviewMessage;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;
}
