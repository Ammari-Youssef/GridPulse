package com.youssef.GridPulse.domain.device.dto;

import lombok.Builder;

@Builder
public record DeviceStats(
        Integer totalDevices,
        Integer onlineDevices,              // ONLINE
        Integer offlineDevices,             // OFFLINE
        Integer maintenanceDevices,         // MAINTENANCE
        Integer criticalAlerts,             // CRITICAL ALERTS
        Integer totalAlerts
) {
}
