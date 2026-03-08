package com.pharmacy.Pharmacy_mgmt.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCreateRequestDTO {

    private String name;

    private String sku;

    private Long pharmacyId;

    private Long categoryId;

    private List<Long> platformIds;

    private List<ProductImageRequestDTO> images;

}
