package com.pharmacy.Pharmacy_mgmt.service;


import java.util.List;

import com.pharmacy.Pharmacy_mgmt.dto.platform.PlatformRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.platform.PlatformResponseDTO;

public interface PlatformService {

    PlatformResponseDTO createPlatform(PlatformRequestDTO request);

    List<PlatformResponseDTO> getAllPlatforms();

}