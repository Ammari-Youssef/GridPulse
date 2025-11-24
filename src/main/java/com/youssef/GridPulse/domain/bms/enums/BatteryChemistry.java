package com.youssef.GridPulse.domain.bms.enums;

/**
 * Enumeration representing the supported battery chemistries
 * within the system. This enum is used to categorize and validate
 * the type of battery associated with a device or BMS.
 *
 * <p>Supported chemistries include:</p>
 * <ul>
 *   <li>{@link #LI_ION} - Lithium-ion batteries, widely used in portable electronics and EVs.</li>
 *   <li>{@link #NI_MH} - Nickel-Metal Hydride batteries, common in hybrid vehicles and consumer devices.</li>
 *   <li>{@link #LEAD_ACID} - Lead-Acid batteries, typically used in automotive and backup power systems.</li>
 *   <li>{@link #LFP} - Lithium Fer Phosphate batteries, known for safety and long cycle life.</li>
 *   <li>{@link #NI_CD} - Nickel-Cadmium batteries, older technology with high durability but less common today.</li>
 * </ul>
 *
 * <p>Using an enum ensures type safety and prevents invalid chemistry
 * values from being persisted or processed.</p>
 */
public enum BatteryChemistry {
    LI_ION,       // Lithium-ion
    NI_MH,        // Nickel-Metal Hydride
    LEAD_ACID,    // Lead-Acid
    LFP,          // Lithium Fer Phosphate
    NI_CD         // Nickel-Cadmium
}

