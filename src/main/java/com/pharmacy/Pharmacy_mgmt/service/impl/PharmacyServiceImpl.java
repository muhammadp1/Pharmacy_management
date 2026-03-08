package com.pharmacy.Pharmacy_mgmt.service.impl;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pharmacy.Pharmacy_mgmt.dto.pharmacy.PharmacyRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.pharmacy.PharmacyResponseDTO;
import com.pharmacy.Pharmacy_mgmt.entity.Pharmacy;
import com.pharmacy.Pharmacy_mgmt.repository.PharmacyRepository;
import com.pharmacy.Pharmacy_mgmt.service.PharmacyService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Override
    public PharmacyResponseDTO createPharmacy(PharmacyRequestDTO request) {

        Pharmacy pharmacy = Pharmacy.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();

        Pharmacy saved = pharmacyRepository.save(pharmacy);

        return PharmacyResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public List<PharmacyResponseDTO> getAllPharmacies() {

        return pharmacyRepository.findAll()
                .stream()
                .map(p -> PharmacyResponseDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .createdAt(p.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
