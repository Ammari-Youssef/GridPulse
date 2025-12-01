package com.youssef.GridPulse.domain.device.dto;

import com.youssef.GridPulse.domain.device.enums.DeviceStatus;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DeviceInput(

        @DecimalMin(value = "-90.0", message = "Latitude must be at least -90.0.")
        @DecimalMax(value = "90.0", message = "Latitude must be at most 90.0.")
        Double gpsLat,

        @DecimalMin(value = "-180.0", message = "Longitude must be at least -180.0.")
        @DecimalMax(value = "180.0", message = "Longitude must be at most 180.0.")
        Double gpsLong,

        @PastOrPresent(message = "Last seen timestamp must be in the past or present.")
        OffsetDateTime lastSeen,

        @NotBlank(message = "Device name is mandatory.")
        @Size(min = 1, max = 255, message = "Device name must be between 1 and 255 characters.")
        String name,

        @NotBlank(message = "Serial number is mandatory.")
        String serialNumber,

        @NotBlank(message = "Status is mandatory.")
        DeviceStatus status,

        @NotBlank(message = "Model is mandatory.")
        @Size(max = 100, message = "Model name must not exceed 100 characters.")
        String model,

        @NotBlank(message = "Manufacturer is mandatory.")
        @Size(max = 100, message = "Manufacturer name must not exceed 100 characters.")
        String manufacturer,

        @NotBlank(message = "Software version is mandatory.")
        @Size(max = 50, message = "Software version must not exceed 50 characters.")
        String softwareVersion,

        @PastOrPresent(message = "Software update time must be in the past or present.")
        OffsetDateTime swUpdateTime,

        @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.(?!$)|$){4}$", message = "Invalid IP address format.")
        String ip,

        @NotNull(message = "User ID is mandatory.")
        UUID userId,

        @NotNull(message = "Operator ID is mandatory.")
        UUID operatorId,

        @NotNull(message = "Inverter ID is mandatory.")
        UUID inverterId,

        @NotNull(message = "BMS ID is mandatory.")
        UUID bmsId,

        @NotNull(message = "Meter ID is mandatory.")
        UUID meterId,

        @NotNull(message = "Fleet ID is mandatory.")
        UUID fleetId
) {
}