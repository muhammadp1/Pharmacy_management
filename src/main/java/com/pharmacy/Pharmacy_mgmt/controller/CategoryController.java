package com.pharmacy.Pharmacy_mgmt.controller;



import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pharmacy.Pharmacy_mgmt.dto.category.CategoryRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.category.CategoryResponseDTO;
import com.pharmacy.Pharmacy_mgmt.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    public CategoryResponseDTO createCategory(
            @RequestBody CategoryRequestDTO request
    ) {
        return categoryService.createCategory(request);
    }

    @GetMapping("/")
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // --------- Delete Category -----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // --------- Bulk Add Categories -----------
    @PostMapping("/bulk")
    public ResponseEntity<List<CategoryResponseDTO>> bulkAddCategories(@RequestBody List<CategoryRequestDTO> requests) {
        List<CategoryResponseDTO> created = categoryService.createCategoriesBulk(requests);
        return ResponseEntity.ok(created);
    }
}
