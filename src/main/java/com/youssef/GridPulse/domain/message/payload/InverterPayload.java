package com.youssef.GridPulse.domain.message.payload;

public record InverterPayload(
        float out_pwr,
        float voltage,
        float current,
        float frequency,
        float efficiency,
        float temperature
) {}
