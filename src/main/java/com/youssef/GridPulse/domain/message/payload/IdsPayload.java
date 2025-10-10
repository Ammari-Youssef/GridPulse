package com.youssef.GridPulse.domain.message.payload;

/**
 * Payload for Intrusion Detection System (IDS) alerts.
 *
 * @param attackType      e.g. "DDoS", Backdoor, Exploit, Dos/DDoS, Worm.
 * @param sourceIp        IP of attacker or suspicious device
 * @param destinationIp
 * @param sourcePort
 * @param destinationPort
 */
public record IdsPayload(
        String attackType,
        String sourceIp,
        String destinationIp,
        int sourcePort,
        int destinationPort,
        String protocol,
        String rawMessage
        ) {
}
