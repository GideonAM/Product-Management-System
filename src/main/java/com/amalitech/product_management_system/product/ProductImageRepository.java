package com.amalitech.product_management_system.product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductImageRepository extends MongoRepository<ProductImage, String> {
}
