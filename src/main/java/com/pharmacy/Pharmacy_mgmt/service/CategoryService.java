package com.pharmacy.Pharmacy_mgmt.service;



import java.util.List;

import com.pharmacy.Pharmacy_mgmt.dto.category.CategoryRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.category.CategoryResponseDTO;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO request);

    List<CategoryResponseDTO> getAllCategories();
}
