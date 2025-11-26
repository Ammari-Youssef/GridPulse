package com.youssef.GridPulse.domain.meter.dto;

public record MeterInput(
        String name,
        String model,
        String manufacturer,
        String version,

        Float powerDispatched,
        Float energyConsumed,
        Float energyProduced

) {
}
