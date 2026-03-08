package com.pharmacy.Pharmacy_mgmt.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.Pharmacy_mgmt.entity.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    Optional<Pharmacy> findByName(String name);
}
