package com.pharmacy.Pharmacy_mgmt.controller;


import com.pharmacy.Pharmacy_mgmt.entity.Product;
import com.pharmacy.Pharmacy_mgmt.entity.ProductImage;
import com.pharmacy.Pharmacy_mgmt.repository.ProductRepository;
import com.pharmacy.Pharmacy_mgmt.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ProductRepository productRepository;

    // 1️⃣ Upload single image to ImgBB → return URL
   @PostMapping("/upload")
public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
        String url = imageService.uploadImageToImgBB(file);
        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        return ResponseEntity.ok(response); // now returns {"url":"https://..."}
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    }
}

    // 2️⃣ Fetch all images for a product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<String>> getImages(@PathVariable Long productId) {
        List<String> urls = imageService.getProductImages(productId);
        return ResponseEntity.ok(urls);
    }

    // 3️⃣ Delete image by ID
    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        boolean deleted = imageService.deleteImage(imageId);
        if (deleted) return ResponseEntity.ok("Deleted successfully");
        return ResponseEntity.badRequest().body("Image not found");
    }

    // // 4️⃣ Optional: Upload multiple images at once → returns list of URLs
    // @PostMapping("/upload/multiple/{productId}")
    // public ResponseEntity<List<String>> uploadMultipleImages(
    //         @PathVariable Long productId,
    //         @RequestParam("files") MultipartFile[] files
    // ) {
    //     Optional<Product> productOpt = productRepository.findById(productId);
    //     if (productOpt.isEmpty()) return ResponseEntity.badRequest().build();

    //     Product product = productOpt.get();
    //     List<String> urls = new ArrayList<>();

    //     for (MultipartFile file : files) {
    //         try {
    //             String url = imageService.uploadImageToImgBB(file);
    //             imageService.saveProductImage(productId, url, product);
    //             urls.add(url);
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }

    //     return ResponseEntity.ok(urls);
    // }
}