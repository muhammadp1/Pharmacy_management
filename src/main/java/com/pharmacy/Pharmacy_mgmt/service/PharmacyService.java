package com.pharmacy.Pharmacy_mgmt.service;

import java.util.List;

import com.pharmacy.Pharmacy_mgmt.dto.pharmacy.PharmacyRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.pharmacy.PharmacyResponseDTO;

public interface PharmacyService {

    PharmacyResponseDTO createPharmacy(PharmacyRequestDTO request);

    List<PharmacyResponseDTO> getAllPharmacies();

}
