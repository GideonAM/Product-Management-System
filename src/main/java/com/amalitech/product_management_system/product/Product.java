package com.amalitech.product_management_system.product;

import com.amalitech.product_management_system.category.Category;
import com.amalitech.product_management_system.common.BaseEntity;
import com.amalitech.product_management_system.review.Review;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantityInStock;

    @Column(nullable = false)
    private String imageUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    @JsonManagedReference
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Transient
    public Double rating() {
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0);
    }
}
