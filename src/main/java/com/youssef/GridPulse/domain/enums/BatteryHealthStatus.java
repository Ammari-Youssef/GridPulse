package com.youssef.GridPulse.domain.enums;

import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.device.entity.Device;

/**
 * Enum representing the health status of a battery.
 * <p>
 *     Used for monitoring battery condition, scheduling maintenance, and alerting users.
 *     This classification is derived from the battery's State of Health (SoH) percentage.
 * </p>
 * <p>
 *     Shared by the two classes {@link Device} and {@link Bms}, this enum provides
 *     a semantic layer for interpreting raw SoH values into meaningful health categories.
 *     It supports filtering, analytics, and UI visualization across the system.
 * </p>
 * <p>
 *     Mapping logic:
 *     <ul>
 *         <li>{@code CRITICAL} — SoH &lt; 20%</li>
 *         <li>{@code DEGRADED} — 20% ≤ SoH &lt; 30%</li>
 *         <li>{@code UNHEALTHY} — 30% ≤ SoH &lt; 50%</li>
 *         <li>{@code HEALTHY} — SoH ≥ 50%</li>
 *         <li>{@code UNKNOWN} — SoH is null or invalid</li>
 *     </ul>
 * </p>
 */

public enum BatteryHealthStatus {
    HEALTHY,
    UNHEALTHY,
    DEGRADED,
    CRITICAL,
    UNKNOWN
}
