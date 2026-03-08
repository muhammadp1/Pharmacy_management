package com.pharmacy.Pharmacy_mgmt.service;
import com.pharmacy.Pharmacy_mgmt.entity.ProductImage;
import com.pharmacy.Pharmacy_mgmt.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ProductImageRepository productImageRepository;

    @Value("${imgbb.api.key}")
    private String imgbbApiKey;

    // Upload single image to ImgBB and return URL
    public String uploadImageToImgBB(MultipartFile file) throws Exception {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("https://api.imgbb.com/1/upload?key=" + imgbbApiKey);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("image", base64Image));
            post.setEntity(new UrlEncodedFormEntity(params));

            var response = client.execute(post);
            String responseBody = new String(response.getEntity().getContent().readAllBytes());

            // Extract URL
            return responseBody.split("\"url\":\"")[1].split("\"")[0];
        }
    }

    
    // Get all images for a product
    public List<String> getProductImages(Long productId) {
        List<ProductImage> images = productImageRepository.findByProductId(productId);
        List<String> urls = new ArrayList<>();
        for (ProductImage img : images) urls.add(img.getImageUrl());
        return urls;
    }

    // Delete image by ID
    public boolean deleteImage(Long imageId) {
        Optional<ProductImage> imgOpt = productImageRepository.findById(imageId);
        if (imgOpt.isPresent()) {
            productImageRepository.deleteById(imageId);
            return true;
        } else {
            return false;
        }
    }
}
