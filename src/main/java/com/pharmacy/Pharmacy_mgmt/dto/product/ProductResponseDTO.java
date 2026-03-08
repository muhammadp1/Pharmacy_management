package com.pharmacy.Pharmacy_mgmt.dto.product;


import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;

    private String name;

    private String sku;

    private String pharmacy;

    private String category;

    private List<String> platforms;

    private List<String> images;

    private LocalDateTime createdAt;

}
