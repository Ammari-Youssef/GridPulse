package com.youssef.GridPulse.domain.message.payload;

public record BmsPayload(
        int batteryId,
        float batteryVolt,
        float batterySoc,   // State of Charge as percentage
        int batteryCycle,
        float batterySoh,   // State of Health as percentage
        float batteryTemperature
) {}

