package com.pharmacy.Pharmacy_mgmt.service;

import com.pharmacy.Pharmacy_mgmt.entity.User;
import com.pharmacy.Pharmacy_mgmt.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create user
    public User createUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("User with email already exists");
        }
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) throws Exception {
        if (!userRepository.existsById(id)) {
            throw new Exception("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}