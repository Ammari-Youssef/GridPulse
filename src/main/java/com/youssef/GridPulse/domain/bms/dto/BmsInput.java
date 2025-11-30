package com.youssef.GridPulse.domain.bms.dto;

import com.youssef.GridPulse.domain.bms.enums.BatteryChemistry;
import com.youssef.GridPulse.domain.bms.enums.BatteryHealthStatus;


public record BmsInput(
        String name,
        String model,
        String manufacturer,
        String version,


        Float soc,
        BatteryHealthStatus soh, // State of Health (%)
        BatteryChemistry batteryChemistry, // Lithium-Ion, Lead-Acid, etc.
        Integer cycles,
        Float temperature,
        Float voltage
) {
}
