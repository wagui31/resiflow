package com.resiflow.dto;

import com.resiflow.entity.Residence;
import java.time.LocalDateTime;

public class ResidenceResponse {

    private final Long id;
    private final String name;
    private final String address;
    private final String code;
    private final LocalDateTime createdAt;
    private final Boolean enabled;

    public ResidenceResponse(
            final Long id,
            final String name,
            final String address,
            final String code,
            final LocalDateTime createdAt,
            final Boolean enabled
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.code = code;
        this.createdAt = createdAt;
        this.enabled = enabled;
    }

    public static ResidenceResponse fromResidence(final Residence residence) {
        return new ResidenceResponse(
                residence.getId(),
                residence.getName(),
                residence.getAddress(),
                residence.getCode(),
                residence.getCreatedAt(),
                residence.getEnabled()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
