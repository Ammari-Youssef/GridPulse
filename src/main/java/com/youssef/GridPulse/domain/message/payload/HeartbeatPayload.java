package com.youssef.GridPulse.domain.message.payload;

public record HeartbeatPayload(
        long uptime // the uptime is a long representing milliseconds since last reboot
) {
}
