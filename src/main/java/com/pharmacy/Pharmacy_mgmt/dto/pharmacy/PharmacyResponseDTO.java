package com.pharmacy.Pharmacy_mgmt.dto.pharmacy;


import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyResponseDTO {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

}
