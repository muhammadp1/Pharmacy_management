package com.pharmacy.Pharmacy_mgmt.dto.pharmacy;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PharmacyRequestDTO {

    @NotBlank
    private String name;

}
