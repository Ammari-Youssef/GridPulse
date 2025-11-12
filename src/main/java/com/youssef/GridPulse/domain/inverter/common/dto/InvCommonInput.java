package com.youssef.GridPulse.domain.inverter.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youssef.GridPulse.domain.inverter.base.SunSpecModelInput;
import jakarta.validation.constraints.*;

import java.util.UUID;

/**
 * JSON mapping preserves original SunSpec point names (e.g. "ID", "L", "Mn").
 * Side Note: @JsonProperty is there for future REST API Remake.
 * It doesn't affect the invCommonInput either because it is defined in schema
 */
public record InvCommonInput(

        @NotNull(message = "Inverter ID is mandatory.")
        UUID inverterId,

        @JsonProperty("L")
        @NotNull(message = "Model length is required.")
        @Min(value = 0, message = "Model length must be zero or positive.")
        Integer modelLength,

        @JsonProperty("Mn")
        @NotBlank(message = "Manufacturer is required.")
        String manufacturer,

        @JsonProperty("Md")
        @NotBlank(message = "Model is required.")
        String model,

        @JsonProperty("Opt")
        @Size(max = 255, message = "Options must not exceed 255 characters.")
        String options,

        @JsonProperty("Vr")
        @Size(max = 100, message = "Version must not exceed 100 characters.")
        String version,

        @JsonProperty("SN")
        @Size(max = 100, message = "Serial number must not exceed 100 characters.")
        String serialNumber,

        @JsonProperty("DA")
        @NotNull(message = "Device address is required.")
        @Min(value = 1, message = "Device address must be between 1 and 247.")
        @Max(value = 247, message = "Device address must be between 1 and 247.")
        Integer deviceAddress,

        @JsonProperty("Pad")
        @Min(value = 0, message = "Pad must be zero or positive.")
        Integer pad
)
 implements SunSpecModelInput
{
        @Override
        public UUID getInverterId() {
            return inverterId;
        }
}
