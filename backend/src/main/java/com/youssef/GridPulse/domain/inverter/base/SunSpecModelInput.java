package com.youssef.GridPulse.domain.inverter.base;

import java.util.UUID;

/**
 * Marker interface for input DTOs representing SunSpec-bound entities.
 * <p>
 * All SunSpec model inputs must include an {@code inverterId} field to establish
 * a parent-child relationship with the associated {@code Inverter} entity.
 * This enforces domain consistency and enables automated linkage during entity creation.
 *
 * <p>Used by generic mappers and services to:
 * <ul>
 *   <li>Extract the parent inverter reference from input</li>
 *   <li>Automate relationship binding in {@code SunSpecModelService}</li>
 *   <li>Support history tracking and resolver operations scoped by inverter</li>
 * </ul>
 *
 * <p>Implemented by input types such as {@code InvCommonInput}, {@code InvSettingsInput}, etc.
 */
public interface SunSpecModelInput {
    UUID getInverterId();
}
