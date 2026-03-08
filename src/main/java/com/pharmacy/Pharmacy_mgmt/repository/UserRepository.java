package com.pharmacy.Pharmacy_mgmt.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmacy.Pharmacy_mgmt.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
