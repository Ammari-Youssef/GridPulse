package com.youssef.GridPulse.domain.inverter.dto;

public record InverterInput(
        String name,
        String model,
        String version,
        String manufacturer
) {}