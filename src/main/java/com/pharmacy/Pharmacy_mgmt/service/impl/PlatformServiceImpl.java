package com.pharmacy.Pharmacy_mgmt.service.impl;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.pharmacy.Pharmacy_mgmt.dto.platform.PlatformRequestDTO;
import com.pharmacy.Pharmacy_mgmt.dto.platform.PlatformResponseDTO;
import com.pharmacy.Pharmacy_mgmt.entity.Platform;
import com.pharmacy.Pharmacy_mgmt.repository.PlatformRepository;
import com.pharmacy.Pharmacy_mgmt.service.PlatformService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {

    private final PlatformRepository platformRepository;

    @Override
    public PlatformResponseDTO createPlatform(PlatformRequestDTO request) {

        Platform platform = Platform.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();

        Platform saved = platformRepository.save(platform);

        return PlatformResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public List<PlatformResponseDTO> getAllPlatforms() {

        return platformRepository.findAll()
                .stream()
                .map(p -> PlatformResponseDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .createdAt(p.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
