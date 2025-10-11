package com.youssef.GridPulse.domain.message.payload;

import com.youssef.GridPulse.domain.message.payload.enums.SoftwareMessageUpdateStatus;
import com.youssef.GridPulse.domain.message.payload.enums.SoftwarePackageType;

import java.time.Instant;

public record SoftwarePayload(
        Instant startTime,
        Instant endTime,
        SoftwarePackageType packageType,
        String version,
        SoftwareMessageUpdateStatus status
) {}

