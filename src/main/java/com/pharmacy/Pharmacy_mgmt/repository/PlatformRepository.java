package com.pharmacy.Pharmacy_mgmt.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.Pharmacy_mgmt.entity.Platform;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Optional<Platform> findByName(String name);
    //long countByStatus(String status);
}
