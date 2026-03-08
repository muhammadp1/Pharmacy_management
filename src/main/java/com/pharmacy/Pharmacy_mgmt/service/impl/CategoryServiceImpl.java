package com.pharmacy.Pharmacy_mgmt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pharmacy.Pharmacy_mgmt.dto.category.CategoryRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.category.CategoryResponseDTO;
import com.pharmacy.Pharmacy_mgmt.entity.Category;
import com.pharmacy.Pharmacy_mgmt.repository.CategoryRepository;
import com.pharmacy.Pharmacy_mgmt.service.CategoryService;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

        private final CategoryRepository categoryRepository;

        @Override
        public CategoryResponseDTO createCategory(CategoryRequestDTO request) {

                Category category = Category.builder()
                                .name(request.getName())
                                .createdAt(LocalDateTime.now())
                                .build();

                Category saved = categoryRepository.save(category);

                return CategoryResponseDTO.builder()
                                .id(saved.getId())
                                .name(saved.getName())
                                .createdAt(saved.getCreatedAt())
                                .build();
        }

        @Override
        public List<CategoryResponseDTO> getAllCategories() {

                return categoryRepository.findAll()
                                .stream()
                                .map(cat -> CategoryResponseDTO.builder()
                                                .id(cat.getId())
                                                .name(cat.getName())
                                                .createdAt(cat.getCreatedAt())
                                                .build())
                                .collect(Collectors.toList());
        }

        // ---------------- New Methods ----------------

        @Override
        public void deleteCategory(Long id) {
                if (!categoryRepository.existsById(id)) {
                        throw new RuntimeException("Category not found with id: " + id);
                }
                categoryRepository.deleteById(id);
        }

        @Override
        @Transactional
        public List<CategoryResponseDTO> createCategoriesBulk(List<CategoryRequestDTO> requests) {
                List<Category> categories = requests.stream()
                                .map(req -> Category.builder()
                                                .name(req.getName())
                                                .createdAt(LocalDateTime.now())
                                                .build())
                                .collect(Collectors.toList());

                List<Category> saved = categoryRepository.saveAll(categories);

                return saved.stream()
                                .map(cat -> CategoryResponseDTO.builder()
                                                .id(cat.getId())
                                                .name(cat.getName())
                                                .createdAt(cat.getCreatedAt())
                                                .build())
                                .collect(Collectors.toList());
        }
}
