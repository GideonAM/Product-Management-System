package com.amalitech.product_management_system.product;

import com.amalitech.product_management_system.category.Category;
import com.amalitech.product_management_system.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    public Product toProduct(ProductDto productDto, MultipartFile productImage) {

        String productImageReference = getProductImageReference(productImage);
        Category category = categoryRepository.findById(productDto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid product category"));

        return Product.builder()
                .title(productDto.title())
                .description(productDto.description())
                .price(productDto.price())
                .quantityInStock(productDto.quantityInStock())
                .imageReference(productImageReference)
                .category(category)
                .build();
    }

    private String getProductImageReference(MultipartFile productImage) {
        String productImageReference = "";
        try {
            ProductImage productImg = ProductImage.builder()
                    .image(productImage.getBytes())
                    .build();
            productImageReference = productImageRepository.save(productImg).getId();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to upload product image");
        }
        return productImageReference;
    }

    public ProductResponseDto toProductResponseDto(Product product) {

        Optional<ProductImage> productImage = productImageRepository.findById(product.getImageReference());
        var image = new byte[0];
        if (productImage.isPresent())
            image = productImage.get().getImage();

        return ProductResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .productImage(image)
                .rating(product.rating())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }

    public Product toUpdatedProduct(Product product, ProductDto productDto, MultipartFile productImage) {
        if (productImage.getSize() > 0) {
            product.setImageReference(getProductImageReference(productImage));
        }

        if (Objects.nonNull(productDto.title()))
            product.setTitle(productDto.title());

        if (Objects.nonNull(productDto.description()))
            product.setDescription(productDto.description());

        if (Objects.nonNull(productDto.price()))
            product.setPrice(productDto.price());

        if (Objects.nonNull(productDto.quantityInStock()))
            product.setQuantityInStock(productDto.quantityInStock());

        return product;
    }
}
