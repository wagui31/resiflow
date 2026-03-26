package com.resiflow.service;

import com.resiflow.dto.CreateResidenceRequest;
import com.resiflow.entity.Residence;
import com.resiflow.repository.ResidenceRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
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
}
