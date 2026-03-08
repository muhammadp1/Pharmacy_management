package com.pharmacy.Pharmacy_mgmt.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.pharmacy.Pharmacy_mgmt.dto.product.ProductCreateRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.product.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO createProduct(ProductCreateRequestDTO request);

    ProductResponseDTO getProduct(Long id);

    void deleteProduct(Long id);

    List<ProductResponseDTO> searchProducts(
            String search,
            Long pharmacyId,
            Long categoryId,
            Long platformId);

    Page<ProductResponseDTO> getAllProducts(int page, int size);

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO updateProduct(Long id, ProductCreateRequestDTO request);

    int bulkUploadCSV(MultipartFile file) throws Exception;

}
