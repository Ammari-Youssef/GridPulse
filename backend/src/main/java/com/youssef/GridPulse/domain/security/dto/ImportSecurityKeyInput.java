package com.youssef.GridPulse.domain.security.dto;

import com.youssef.GridPulse.domain.security.enums.KeySource;
import com.youssef.GridPulse.domain.security.enums.SecurityType;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ImportSecurityKeyInput(
        @NotBlank @Size(max = 255) String name,
        @NotNull UUID deviceId,
        @NotNull UUID ownerId,
        @NotNull SecurityType securityType,
        @NotBlank @Size(max = 255) String serialNumber,
        @NotBlank String publicKey,
        @NotBlank String privateKey, // accepted only here
        @NotNull KeySource keySource,
        OffsetDateTime revokedTimestamp) {}

