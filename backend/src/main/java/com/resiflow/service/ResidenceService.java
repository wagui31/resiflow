package com.resiflow.service;

import com.resiflow.dto.CreateResidenceRequest;
import com.resiflow.entity.Residence;
import com.resiflow.repository.ResidenceRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class ResidenceService {

    private final ResidenceRepository residenceRepository;

    public ResidenceService(final ResidenceRepository residenceRepository) {
        this.residenceRepository = residenceRepository;
    }

    public Residence createResidence(final CreateResidenceRequest request) {
        validateCreateRequest(request);

        Residence residence = new Residence();
        residence.setName(request.getName().trim());
        residence.setAddress(request.getAddress().trim());
        residence.setCode(resolveResidenceCode(request.getCode(), null));
        residence.setEnabled(request.getEnabled() == null || request.getEnabled());
        residence.setCreatedAt(LocalDateTime.now());

        return residenceRepository.save(residence);
    }

    public List<Residence> getAllResidences() {
        return residenceRepository.findAll();
    }

    public Residence updateResidence(final Long residenceId, final CreateResidenceRequest request) {
        validateCreateRequest(request);

        Residence residence = getRequiredResidence(residenceId);
        residence.setName(request.getName().trim());
        residence.setAddress(request.getAddress().trim());
        if (!isBlank(request.getCode())) {
            residence.setCode(resolveResidenceCode(request.getCode(), residenceId));
        }
        residence.setEnabled(request.getEnabled() == null || request.getEnabled());

        return residenceRepository.save(residence);
    }

    public void deleteResidence(final Long residenceId) {
        Residence residence = getRequiredResidence(residenceId);
        residenceRepository.delete(residence);
    }

    public Residence getRequiredResidence(final Long residenceId) {
        if (residenceId == null) {
            throw new IllegalArgumentException("Residence ID must not be null");
        }

        return residenceRepository.findById(residenceId)
                .orElseThrow(() -> new NoSuchElementException("Residence not found: " + residenceId));
    }

    public Residence getRequiredResidenceByCode(final String residenceCode) {
        if (isBlank(residenceCode)) {
            throw new IllegalArgumentException("Residence code must not be blank");
        }

        return residenceRepository.findByCode(residenceCode.trim())
                .orElseThrow(() -> new NoSuchElementException("Invalid residence code"));
    }

    private void validateCreateRequest(final CreateResidenceRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Create residence request must not be null");
        }
        if (isBlank(request.getName())) {
            throw new IllegalArgumentException("Residence name must not be blank");
        }
        if (isBlank(request.getAddress())) {
            throw new IllegalArgumentException("Residence address must not be blank");
        }
    }

    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }

    private String resolveResidenceCode(final String requestedCode, final Long residenceId) {
        String candidate = isBlank(requestedCode) ? generateUniqueCode() : requestedCode.trim().toUpperCase();

        boolean alreadyUsed = residenceRepository.existsByCode(candidate);
        if (alreadyUsed && residenceId != null) {
            Residence existingResidence = residenceRepository.findByCode(candidate).orElse(null);
            if (existingResidence != null && residenceId.equals(existingResidence.getId())) {
                return candidate;
            }
        }

        if (alreadyUsed) {
            throw new IllegalArgumentException("Residence code is already used");
        }

        return candidate;
    }

    private String generateUniqueCode() {
        for (int attempt = 0; attempt < 20; attempt++) {
            String candidate = "RES-" + randomAlphaNumeric(6);
            if (!residenceRepository.existsByCode(candidate)) {
                return candidate;
            }
        }

        throw new IllegalStateException("Unable to generate a unique residence code");
    }

    private String randomAlphaNumeric(final int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder(length);
        for (int index = 0; index < length; index++) {
            int selectedIndex = ThreadLocalRandom.current().nextInt(characters.length());
            builder.append(characters.charAt(selectedIndex));
        }
        return builder.toString();
    }
}
