package com.youssef.GridPulse.domain.message.dto;

import java.time.Instant;

/**
 * a Message structure coming from a device 
 * @param messageId // the unique id of the message
 * @param base64Message // the base64-encoded message content with a certain payload structure (IDS, Meter, BMS, Heartbeat, Software, etc.)
 * @param date // the timestamp when the message was created on the device
 * @param priority
 * @param format
 * @param status
 * @param type
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
