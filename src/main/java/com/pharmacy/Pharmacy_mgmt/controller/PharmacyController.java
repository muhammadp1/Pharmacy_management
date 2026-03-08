package com.pharmacy.Pharmacy_mgmt.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.pharmacy.Pharmacy_mgmt.dto.pharmacy.PharmacyRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.pharmacy.PharmacyResponseDTO;
import com.pharmacy.Pharmacy_mgmt.repository.PharmacyRepository;
import com.pharmacy.Pharmacy_mgmt.service.PharmacyService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;
    private final PharmacyRepository pharmacyRepository;

    @PostMapping
    public PharmacyResponseDTO createPharmacy(
            @RequestBody PharmacyRequestDTO request
    ) {
        return pharmacyService.createPharmacy(request);
    }

    @GetMapping
    public List<PharmacyResponseDTO> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
    }

     @GetMapping("/count")
    public Map<String, Long> getPharmacyCount() {
        long count = pharmacyRepository.count();
        return Map.of("count", count);
    }
}
