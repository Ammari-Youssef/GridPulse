package com.youssef.GridPulse.domain.bms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record BmsInput(
        @NotBlank String name,
        String model,
        String manufacturer,
        String batteryChemistry,
        @Max(value = 100) @PositiveOrZero Float soc,
        String soh,
        String version
) {
}
