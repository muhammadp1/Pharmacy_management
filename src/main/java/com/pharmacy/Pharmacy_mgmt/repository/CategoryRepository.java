package com.pharmacy.Pharmacy_mgmt.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.Pharmacy_mgmt.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
