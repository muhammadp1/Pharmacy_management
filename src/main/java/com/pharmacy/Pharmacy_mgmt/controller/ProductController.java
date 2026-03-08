package com.pharmacy.Pharmacy_mgmt.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.pharmacy.Pharmacy_mgmt.dto.product.ProductCreateRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.product.ProductResponseDTO;
import com.pharmacy.Pharmacy_mgmt.repository.ProductRepository;
import com.pharmacy.Pharmacy_mgmt.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // allows all origins for this controller
@RequestMapping("/products")

@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping
    public ProductResponseDTO createProduct(
            @RequestBody ProductCreateRequestDTO request) {
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProduct(
            @PathVariable Long id) {
        return productService.getProduct(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<ProductResponseDTO> searchProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long pharmacyId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long platformId) {
        return productService.searchProducts(
                search,
                pharmacyId,
                categoryId,
                platformId);
    }

    @GetMapping
    public Page<ProductResponseDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProducts(page, size);
    }

    @GetMapping("/{id}/GetProduct")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}/update")
    public ProductResponseDTO updateProduct(
            @PathVariable Long id,
            @RequestBody ProductCreateRequestDTO request) {
        return productService.updateProduct(id, request);
    }

     @PostMapping("/bulk-upload")
    public ResponseEntity<?> bulkUpload(@RequestParam("file") MultipartFile file) {
        try {
            int count = productService.bulkUploadCSV(file);
            return ResponseEntity.ok(Map.of("message", count + " products uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/count")
    public Map<String, Long> getProductCount() {
        long count = productRepository.count();
        return Map.of("count", count);
    }
}