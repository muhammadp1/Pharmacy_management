package com.pharmacy.Pharmacy_mgmt.repository;

import com.pharmacy.Pharmacy_mgmt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // @Query("""
    // SELECT DISTINCT p FROM Product p
    // LEFT JOIN ProductPlatform pp ON pp.product = p
    // WHERE
    // (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
    // OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :search, '%')))
    // AND (:pharmacyId IS NULL OR p.pharmacy.id = :pharmacyId)
    // AND (:categoryId IS NULL OR p.category.id = :categoryId)
    // AND (:platformId IS NULL OR pp.platform.id = :platformId)
    // """)
    // List<Product> searchProducts(
    // String search,
    // Long pharmacyId,
    // Long categoryId,
    // Long platformId
    // );

    @Query("""
            SELECT DISTINCT p FROM Product p
            LEFT JOIN p.productPlatforms pp
            WHERE (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:pharmacyId IS NULL OR p.pharmacy.id = :pharmacyId)
            AND (:categoryId IS NULL OR p.category.id = :categoryId)
            AND (:platformId IS NULL OR pp.platform.id = :platformId)
            """)
    List<Product> searchProducts(
            @Param("search") String search,
            @Param("pharmacyId") Long pharmacyId,
            @Param("categoryId") Long categoryId,
            @Param("platformId") Long platformId);
}