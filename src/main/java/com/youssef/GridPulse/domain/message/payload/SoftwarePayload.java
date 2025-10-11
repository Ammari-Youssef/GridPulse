package com.youssef.GridPulse.domain.message.payload;

import com.youssef.GridPulse.domain.message.payload.enums.SoftwareMessageUpdateStatus;
import com.youssef.GridPulse.domain.message.payload.enums.SoftwarePackageType;

import java.time.Instant;

/**
 * Represents the payload structure for a software update message.
 * <p>
 * This message is generated when a software update is available for a device.
 * It includes metadata about the update window, the type of software package being updated,
 * the version being applied, and the outcome status of the update operation.
 * </p>
 *
 * <p><strong>Fields:</strong></p>
 * <ul>
 *   <li><strong>startTime</strong> – Timestamp indicating when the software update began.</li>
 *   <li><strong>endTime</strong> – Timestamp indicating when the software update completed.</li>
 *   <li><strong>packageType</strong> – Type of software package being updated. See {@link SoftwarePackageType}:
 *     <ul>
 *       <li>{@code OS} (0)</li>
 *       <li>{@code COMMS} (1)</li>
 *       <li>{@code IDS} (2)</li>
 *       <li>{@code BMS} (3)</li>
 *       <li>{@code INV} (4)</li>
 *       <li>{@code METER} (5)</li>
 *       <li>{@code API} (6)</li>
 *     </ul>
 *   </li>
 *   <li><strong>version</strong> – Version string of the new running software.</li>
 *   <li><strong>status</strong> – Result of the update operation. See {@link SoftwareMessageUpdateStatus}:
 *     <ul>
 *       <li>{@code SUCCESS} (200) – Update succeeded</li>
 *       <li>{@code FAILURE} (500) – Update failed</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <p>This payload supports device lifecycle management, update tracking, and audit logging.</p>
 */
public record SoftwarePayload(
        Instant startTime,
        Instant endTime,
        SoftwarePackageType packageType,
        String version,
        SoftwareMessageUpdateStatus status
) {}


