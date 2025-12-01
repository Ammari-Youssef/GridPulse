package com.youssef.GridPulse.domain.message.payload;

/**
 * Represents the payload structure for a Heartbeat message.
 * <p>
 * A Heartbeat message is sent periodically from the device to the cloud to confirm that the device
 * is online and functioning properly. It provides a snapshot of the device's uptime for monitoring
 * and diagnostics.
 * </p>
 *
 * <p><strong>Fields:</strong></p>
 * <ul>
 *   <li><strong>uptime</strong> – Uptime of the device in milliseconds since the last reboot.</li>
 * </ul>
 *
 * <p>This payload is used for system health checks, availability tracking, and cloud-side device monitoring.</p>
 */
public record HeartbeatPayload(
        long uptime
) {}
