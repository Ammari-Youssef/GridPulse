package com.youssef.GridPulse.domain.device.enums;

/**
 * Represents the operational status of a device.
 * <p>
 * Used for lifecycle tracking, dashboard filtering, and alerting logic.
 * Each value maps to a meaningful state in the system's monitoring flow.
 */
public enum DeviceStatus {
    // Core Operational states
    ONLINE,          // Device is actively connected and reporting data
    OFFLINE,         // Device is unreachable or not transmitting
    MAINTENANCE,     // Device is undergoing diagnostics or service
    ERROR,           // Device has encountered a fault or failure
    NEW,             // Device has been registered but not yet commissioned
    UNKNOWN,          // Device status is not yet determined or reported

    // Lifecycle states
    COMMISSIONING,   // Device is in setup or calibration phase
    STANDBY,         // Device is online but idle or inactive
    DECOMMISSIONED,  // Device has been permanently removed from service
    RETIRED       // Device has reached end-of-life and is archived
    }

