package com.pharmacy.Pharmacy_mgmt.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.Pharmacy_mgmt.entity.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);
}
