package com.pharmacy.Pharmacy_mgmt.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pharmacy.Pharmacy_mgmt.entity.ProductPlatform;

public interface ProductPlatformRepository extends JpaRepository<ProductPlatform, Long> {

    @Modifying
@Query("DELETE FROM ProductPlatform pp WHERE pp.product.id = :productId")
void deleteAllByProductId(@Param("productId") Long productId);
}