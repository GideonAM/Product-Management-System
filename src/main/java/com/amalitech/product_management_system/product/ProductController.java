package com.amalitech.product_management_system.product;

import com.amalitech.product_management_system.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE', 'MANAGER_WRITE')")
    public ResponseEntity<String> createProduct(@Valid @RequestPart(name = "productDto") ProductDto productDto,
                                                @RequestParam(name = "productImage") MultipartFile productImage) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto, productImage));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponseDto>> getAllProduct(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @DeleteMapping("/{product-id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE', 'MANAGER_WRITE')")
    public ResponseEntity<String> deleteProductById(@PathVariable("product-id") String productId) {
        return ResponseEntity.ok(productService.deleteProductById(productId));
    }

    @PutMapping("/{product-id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_WRITE', 'MANAGER_WRITE')")
    public ResponseEntity<String> updateProduct(
            @PathVariable("product-id") String productId,
            @RequestPart ProductDto productDto,
            @RequestParam(name = "productImage", required = false) MultipartFile productImage
    ) {
        return ResponseEntity.ok(productService.updateProduct(productId, productDto, productImage));
    }
}
