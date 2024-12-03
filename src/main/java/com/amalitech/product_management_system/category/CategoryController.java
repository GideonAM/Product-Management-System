package com.amalitech.product_management_system.category;

import com.amalitech.product_management_system.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PageResponse<CategoryDto>> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(page, size));
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable String id) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

}
