package com.pharmacy.Pharmacy_mgmt.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pharmacy.Pharmacy_mgmt.dto.product.ProductCreateRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.product.ProductImageRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.product.ProductResponseDTO;
import com.pharmacy.Pharmacy_mgmt.entity.Category;
import com.pharmacy.Pharmacy_mgmt.entity.Pharmacy;
import com.pharmacy.Pharmacy_mgmt.entity.Platform;
import com.pharmacy.Pharmacy_mgmt.entity.Product;
import com.pharmacy.Pharmacy_mgmt.entity.ProductImage;
import com.pharmacy.Pharmacy_mgmt.entity.ProductPlatform;
import com.pharmacy.Pharmacy_mgmt.repository.CategoryRepository;
import com.pharmacy.Pharmacy_mgmt.repository.PharmacyRepository;
import com.pharmacy.Pharmacy_mgmt.repository.PlatformRepository;
import com.pharmacy.Pharmacy_mgmt.repository.ProductImageRepository;
import com.pharmacy.Pharmacy_mgmt.repository.ProductPlatformRepository;
import com.pharmacy.Pharmacy_mgmt.repository.ProductRepository;
import com.pharmacy.Pharmacy_mgmt.service.ProductService;

import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;
        private final PharmacyRepository pharmacyRepository;
        private final CategoryRepository categoryRepository;
        private final PlatformRepository platformRepository;
        private final ProductImageRepository productImageRepository;
        private final ProductPlatformRepository productPlatformRepository;

        @Override
        public ProductResponseDTO createProduct(ProductCreateRequestDTO request) {

                Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));

                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                Product product = new Product();
                product.setName(request.getName());
                product.setSku(request.getSku());
                product.setPharmacy(pharmacy);
                product.setCategory(category);

                productRepository.save(product);

                if (request.getImages() != null) {

                        for (ProductImageRequestDTO img : request.getImages()) {

                                ProductImage image = new ProductImage();
                                image.setProduct(product);
                                image.setImageUrl(img.getImageUrl());
                                image.setIsPrimary(img.getIsPrimary());

                                productImageRepository.save(image);
                        }
                }

                if (request.getPlatformIds() != null) {

                        for (Long platformId : request.getPlatformIds()) {

                                Platform platform = platformRepository.findById(platformId)
                                                .orElseThrow(() -> new RuntimeException("Platform not found"));

                                ProductPlatform mapping = new ProductPlatform();
                                mapping.setProduct(product);
                                mapping.setPlatform(platform);

                                productPlatformRepository.save(mapping);
                        }
                }

                return mapToResponse(product);
        }

        @Override
        public ProductResponseDTO getProduct(Long id) {

                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                return mapToResponse(product);
        }

        @Override
        public void deleteProduct(Long id) {

                productRepository.deleteById(id);

        }

        @Override
        public List<ProductResponseDTO> searchProducts(
                        String search,
                        Long pharmacyId,
                        Long categoryId,
                        Long platformId) {

                List<Product> products = productRepository.searchProducts(
                                search,
                                pharmacyId,
                                categoryId,
                                platformId);

                return products.stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public Page<ProductResponseDTO> getAllProducts(int page, int size) {

                Pageable pageable = PageRequest.of(page, size);

                Page<Product> products = productRepository.findAll(pageable);

                return products.map(this::mapToResponse);
        }

        @Override
        public ProductResponseDTO getProductById(Long id) {

                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                return mapToResponse(product);
        }

        @Override
        @Transactional
        public ProductResponseDTO updateProduct(Long id, ProductCreateRequestDTO request) {

                // 1️⃣ Fetch existing product
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

                // 2️⃣ Update basic fields
                product.setName(request.getName());
                product.setSku(request.getSku());

                // 3️⃣ Update Category
                Category category = categoryRepository.findById(request.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found"));
                product.setCategory(category);

                // 4️⃣ Update Pharmacy
                Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
                product.setPharmacy(pharmacy);

                // 5️⃣ Update Platforms
                // Clear old mappings first
                // 5️⃣ Update Platforms safely
                // Delete old mappings in DB first
                productPlatformRepository.deleteAllByProductId(product.getId());
                product.getProductPlatforms().clear();

                if (request.getPlatformIds() != null) {
                        for (Long platformId : request.getPlatformIds()) {
                                Platform platform = platformRepository.findById(platformId)
                                                .orElseThrow(() -> new RuntimeException("Platform not found"));

                                ProductPlatform mapping = new ProductPlatform();
                                mapping.setProduct(product);
                                mapping.setPlatform(platform);

                                productPlatformRepository.save(mapping); // save individually
                                product.getProductPlatforms().add(mapping); // maintain relation in entity
                        }
                }

                // 6️⃣ Update Images
                // Clear old images first
                productImageRepository.deleteAll(product.getImages());
                product.getImages().clear();

                if (request.getImages() != null) {
                        for (ProductImageRequestDTO imgDTO : request.getImages()) {
                                ProductImage image = new ProductImage();
                                image.setProduct(product);
                                image.setImageUrl(imgDTO.getImageUrl());
                                image.setIsPrimary(imgDTO.getIsPrimary());

                                product.getImages().add(image);
                        }
                }

                // 7️⃣ Save product
                Product updatedProduct = productRepository.save(product);

                // 8️⃣ Map to response DTO
                return mapToResponse(updatedProduct);
        }

        private ProductResponseDTO mapToResponse(Product product) {

                List<String> images = product.getImages()
                                .stream()
                                .map(ProductImage::getImageUrl)
                                .toList();

                List<String> platforms = product.getProductPlatforms()
                                .stream()
                                .map(pp -> pp.getPlatform().getName())
                                .toList();

                return ProductResponseDTO.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .sku(product.getSku())
                                .pharmacy(product.getPharmacy().getName())
                                .category(product.getCategory().getName())
                                .images(images)
                                .platforms(platforms)
                                .createdAt(product.getCreatedAt())
                                .build();
        }
        @Override
        public int bulkUploadCSV(MultipartFile file) throws Exception {
                if (file.isEmpty())
                        throw new Exception("CSV file is empty");

                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String line;
                int count = 0;

                // Skip header
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                        String[] columns = line.split(",");
                        if (columns.length < 6)
                                continue;

                        String name = columns[0].trim();
                        String sku = columns[1].trim();
                        String categoryName = columns[2].trim();
                        String platformsCsv = columns[3].trim();
                        String pharmacyName = columns[4].trim();
                        String imagesCsv = columns[5].trim();

                        Category category = categoryRepository.findByName(categoryName)
                                        .orElseThrow(() -> new Exception("Category not found: " + categoryName));

                        Pharmacy pharmacy = pharmacyRepository.findByName(pharmacyName)
                                        .orElseThrow(() -> new Exception("Pharmacy not found: " + pharmacyName));

                        List<Platform> platforms = Arrays.stream(platformsCsv.split("\\|"))
                                        .map(String::trim)
                                        .map(nameStr -> platformRepository.findByName(nameStr)
                                                        .orElseThrow(() -> new RuntimeException(
                                                                        "Platform not found: " + nameStr)))
                                        .collect(Collectors.toList());

                        List<String> images = Arrays.stream(imagesCsv.split("\\|"))
                                        .map(String::trim)
                                        .collect(Collectors.toList());

                        // Create product
                        Product product = new Product();
                        product.setName(name);
                        product.setSku(sku);
                        product.setCategory(category);
                        product.setPharmacy(pharmacy);

                        // Map to ProductPlatform entities
                        List<ProductPlatform> productPlatforms = platforms.stream()
                                        .map(platform -> {
                                                ProductPlatform pp = new ProductPlatform();
                                                pp.setPlatform(platform);
                                                pp.setProduct(product);
                                                return pp;
                                        }).collect(Collectors.toList());
                        product.setProductPlatforms(productPlatforms);

                        // Map to ProductImage entities
                        List<ProductImage> productImages = images.stream()
                                        .map(url -> {
                                                ProductImage pi = new ProductImage();
                                                pi.setImageUrl(url);
                                                pi.setProduct(product);
                                                pi.setIsPrimary(false); // optional
                                                return pi;
                                        }).collect(Collectors.toList());
                        product.setImages(productImages);

                        productRepository.save(product);
                        count++;
                }

                return count;
        }
}
