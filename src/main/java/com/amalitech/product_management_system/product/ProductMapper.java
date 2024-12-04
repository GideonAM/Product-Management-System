package com.amalitech.product_management_system.product;

import com.amalitech.product_management_system.category.Category;
import com.amalitech.product_management_system.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.io.File.separator;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    @Value("${application.file.upload-path}")
    private String productImageFolder;

    private final CategoryRepository categoryRepository;

    public Product toProduct(ProductDto productDto, MultipartFile productImage) {
        String productImageUrl = getProductImageUrl(productImage);
        Category category = categoryRepository.findById(productDto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Invalid product category"));

        return Product.builder()
                .title(productDto.title())
                .description(productDto.description())
                .price(productDto.price())
                .quantityInStock(productDto.quantityInStock())
                .imageUrl(productImageUrl)
                .category(category)
                .build();
    }

    public ProductResponseDto toProductResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .productImageUrl(product.getImageUrl())
                .rating(product.rating())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }

    private String getProductImageUrl(MultipartFile productImage) {
        String targetFolder = productImageFolder;
        File fileFolder = new File(targetFolder);

        if (!fileFolder.exists()) {
            boolean created = fileFolder.mkdir();
            if (!created) {
                throw new RuntimeException("Failed to upload product image");
            }
        }

        String filePath = fileFolder + separator + System.currentTimeMillis()
                + getImageExtension(productImage.getOriginalFilename());
        Path path = Paths.get(filePath);

        try {
            Files.write(path, productImage.getBytes());
            return filePath;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to upload product image");
        }
    }

    private String getImageExtension(String productImageName) {
        if (productImageName == null)
            return "";

        int index = productImageName.lastIndexOf(".");
        if (index == -1)
            return "";

        return productImageName.substring(index).toLowerCase();
    }

    public Product toUpdatedProduct(Product product, ProductDto productDto, MultipartFile productImage) {
        if (productImage.getSize() > 0) {
            product.setImageUrl(getProductImageUrl(productImage));
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
