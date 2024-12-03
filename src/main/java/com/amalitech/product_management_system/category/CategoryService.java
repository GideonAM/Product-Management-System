package com.amalitech.product_management_system.category;

import com.amalitech.product_management_system.common.PageResponse;
import com.amalitech.product_management_system.product.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public PageResponse<CategoryDto> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> pageCategories = categoryRepository.findAll(pageable);
        List<CategoryDto> categoriesResponse = pageCategories.stream()
                .map(category -> new CategoryDto(category.getCategoryName(), category.getId()))
                .toList();

        return new PageResponse<>(
                categoriesResponse,
                pageCategories.isFirst(),
                pageCategories.isLast(),
                pageCategories.getTotalPages(),
                pageCategories.getTotalElements()
        );
    }

    public String createCategory(CategoryDto categoryDto) {
        Category category = Category.builder()
                .categoryName(categoryDto.categoryName())
                .build();
        categoryRepository.save(category);

        return "Category added successfully";
    }

    public String updateCategory(CategoryDto categoryDto, String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product to be updated not found"));

        category.setCategoryName(categoryDto.categoryName());
        categoryRepository.save(category);
        return "Category updated successfully";
    }

    public String deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product to be deleted not found"));
        categoryRepository.delete(category);
        return "Category deleted";
    }
}
