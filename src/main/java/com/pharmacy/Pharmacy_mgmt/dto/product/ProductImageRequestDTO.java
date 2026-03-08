package com.pharmacy.Pharmacy_mgmt.dto.product;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageRequestDTO {

    private String imageUrl;
    private Boolean isPrimary;

}
