package com.pharmacy.Pharmacy_mgmt.dto.category;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank
    private String name;
}
