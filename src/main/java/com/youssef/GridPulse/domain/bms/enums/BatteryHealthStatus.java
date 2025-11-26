package com.youssef.GridPulse.domain.bms.enums;

import com.youssef.GridPulse.domain.bms.entity.Bms;

/**
 * Enumeration of battery health categories derived from the State of Health (SoH) percentage.
 *
 * <p>This enum provides a semantic interpretation of raw SoH values, enabling
 * consistent monitoring, maintenance scheduling, and user alerting across the system.</p>
 *
 * <p>It is primarily used by the {@link Bms} entity to represent battery condition,
 * ensuring a unified and type-safe classification for filtering, analytics,
 * and UI visualization.</p>
 *
 * <p>Classification thresholds:</p>
 * <ul>
 *   <li>{@link #CRITICAL} — SoH less than 20%</li>
 *   <li>{@link #DEGRADED} — SoH between 20% (inclusive) and 30%</li>
 *   <li>{@link #UNHEALTHY} — SoH between 30% (inclusive) and 50%</li>
 *   <li>{@link #HEALTHY} — SoH greater than or equal to 50%</li>
 *   <li>{@link #UNKNOWN} — SoH is null, out of range, or invalid</li>
 * </ul>
 *
 * <p>Using this enum enforces type safety and avoids ambiguous interpretations
 * of battery health values throughout the application.</p>
 */
public enum BatteryHealthStatus {
    HEALTHY,
    UNHEALTHY,
    DEGRADED,
    CRITICAL,
    UNKNOWN
}
