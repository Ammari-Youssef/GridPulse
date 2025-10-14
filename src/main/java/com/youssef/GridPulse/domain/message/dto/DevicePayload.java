package com.youssef.GridPulse.domain.message.dto;

import java.time.Instant;

/**
 * Represents the structure of a message received from an IoT device.
 * <p>
 * This payload encapsulates metadata and raw content for a device-generated message.
 * The {@code base64Message} field contains a Base64-encoded JSON string representing
 * one of the supported payload types: IDS, BMS, METER, INVERTER, SYSTEM, SOFTWARE, HEARTBEAT, etc.
 * </p>
 *
 * <p><strong>Fields:</strong></p>
 * <ul>
 *   <li><strong>messageId</strong> – Unique identifier of the message.</li>
 *   <li><strong>base64Message</strong> – Base64-encoded message content containing a typed payload structure.</li>
 *   <li><strong>date</strong> – Timestamp indicating when the message was created on the device.</li>
 *   <li><strong>priority</strong> – Priority level assigned to the message (e.g., HIGH, MEDIUM, LOW).</li>
 *   <li><strong>format</strong> – Format of the message content (e.g., JSON, XML).</li>
 *   <li><strong>status</strong> – Processing or delivery status of the message (e.g., RECEIVED, PROCESSED, FAILED).</li>
 *   <li><strong>type</strong> – Logical type of the message (e.g., IDS, SYSTEM, METER), used to route and decode the payload.</li>
 * </ul>
 *
 * <p><strong>Parsing:</strong></p>
 * <p>
 * The {@code fromRaw(String raw)} method allows parsing a pipe-delimited raw string into a structured {@code DevicePayload}.
 * Expected format: {@code messageId|base64Message|date|priority|format|status|type}
 * </p>
 *
 * <p>This structure supports ingestion, decoding, and routing of device messages for telemetry, diagnostics, and alerting workflows.</p>
 */
public record DevicePayload(
        String messageId,
        String base64Message,
        Instant date,
        String priority,
        String format,
        String status,
        String type
) {
    public static DevicePayload fromRaw(String raw) {
        String[] parts = raw.split("\\|");
        return new DevicePayload(
                parts[0],
                parts[1],
                Instant.parse(parts[2]),
                parts[3],
                parts[4],
                parts[5],
                parts[6]
        );
    }
}
