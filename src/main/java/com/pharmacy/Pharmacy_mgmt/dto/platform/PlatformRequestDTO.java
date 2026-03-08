package com.pharmacy.Pharmacy_mgmt.dto.platform;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformRequestDTO {

    @NotBlank
    private String name;

}
