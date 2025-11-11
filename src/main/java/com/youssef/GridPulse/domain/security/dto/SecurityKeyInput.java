package com.youssef.GridPulse.domain.security.dto;

import com.youssef.GridPulse.domain.security.enums.KeySource;
import com.youssef.GridPulse.domain.security.enums.KeyStatus;
import com.youssef.GridPulse.domain.security.enums.SecurityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.UUID;
public record SecurityKeyInput(
        @NotBlank @Size(max = 255) String name,
        @NotNull UUID deviceId,
        @NotNull UUID ownerId,
        @NotNull SecurityType securityType,
        @NotBlank @Size(max = 255) String serialNumber,
        @NotBlank String publicKey,
        @NotNull KeySource keySource,
        @NotNull KeyStatus status,
        OffsetDateTime revokedTimestamp
) {}
