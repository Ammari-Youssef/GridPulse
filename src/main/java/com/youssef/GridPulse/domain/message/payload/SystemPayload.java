package com.youssef.GridPulse.domain.message.payload;

public record SystemPayload(
        int cpuUsage,       // percentage
        int memoryUsage,    // percentage
        int diskUsage,      // percentage
        int temperature     // in Celsius
) {}

