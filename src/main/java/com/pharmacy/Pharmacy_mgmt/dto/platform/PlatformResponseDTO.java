package com.pharmacy.Pharmacy_mgmt.dto.platform;


import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformResponseDTO {

    private Long id;
    private String name;
    private LocalDateTime createdAt;

}
