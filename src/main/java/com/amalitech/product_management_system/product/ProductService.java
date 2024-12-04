package com.amalitech.product_management_system.product;

import com.amalitech.product_management_system.common.PageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public String createProduct(ProductDto productDto, MultipartFile productImage) {
        Product product = productMapper.toProduct(productDto, productImage);
        productRepository.save(product);
        return "Product created successfully";
    }

    public PageResponse<ProductResponseDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> pageProducts = productRepository.findAll(pageable);

        List<ProductResponseDto> productsResponse = pageProducts.stream()
                .map(productMapper::toProductResponseDto)
                .toList();

        return new PageResponse <> (
                productsResponse,
                pageProducts.isFirst(),
                pageProducts.isLast(),
                pageProducts.getTotalPages(),
                pageProducts.getTotalElements()
        );
    }

    public String deleteProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        productRepository.delete(product);
        return "Product deleted successfully";
    }

    public String updateProduct(String productId, ProductDto productDto, MultipartFile productImage) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Product updatedProduct = productMapper.toUpdatedProduct(product, productDto, productImage);
        productRepository.save(updatedProduct);

        return "Product updated successfully";
    }
}
