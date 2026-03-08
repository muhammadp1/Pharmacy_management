package com.pharmacy.Pharmacy_mgmt.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.pharmacy.Pharmacy_mgmt.dto.platform.PlatformRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.platform.PlatformResponseDTO;
import com.pharmacy.Pharmacy_mgmt.repository.PlatformRepository;
import com.pharmacy.Pharmacy_mgmt.service.PlatformService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {

    private final PlatformService platformService;
    private final PlatformRepository platformRepository;

    @PostMapping
    public PlatformResponseDTO createPlatform(
            @RequestBody PlatformRequestDTO request
    ) {
        return platformService.createPlatform(request);
    }

    @GetMapping
    public List<PlatformResponseDTO> getAllPlatforms() {
        return platformService.getAllPlatforms();
    }

    @GetMapping("/count")
    public Map<String, Long> getPlatformCount(@RequestParam(required = false) String status) {
        long count;
        if (status != null && !status.isEmpty()) {
            count = platformRepository.count();
        } else {
            count = platformRepository.count();
        }
        return Map.of("count", count);
    }
}
