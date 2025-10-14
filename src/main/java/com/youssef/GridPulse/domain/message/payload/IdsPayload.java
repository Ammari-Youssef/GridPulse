package com.youssef.GridPulse.domain.message.payload;

import com.youssef.GridPulse.domain.message.payload.enums.AttackType;

/**
 * Payload structure for an Intrusion Detection System (IDS) alert message.
 * <p>
 * This message is generated when a potential security threat is detected by the IDS module.
 * It contains metadata about the nature of the attack, the network endpoints involved,
 * and the raw message content for deeper inspection or logging.
 * </p>
 *
 * <p><strong>Fields:</strong></p>
 * <ul>
 *   <li><strong>attackType</strong> – Type of detected attack (e.g., {@code DDoS}, {@code Backdoor}, {@code Exploit}, {@code Worm}). See {@link AttackType}.</li>
 *   <li><strong>sourceIp</strong> – IP address of the attacker or suspicious origin.</li>
 *   <li><strong>destinationIp</strong> – IP address of the targeted device or service.</li>
 *   <li><strong>sourcePort</strong> – Network port used by the attacker to initiate the connection.</li>
 *   <li><strong>destinationPort</strong> – Network port on the target device that was accessed or probed.</li>
 *   <li><strong>protocol</strong> – Network protocol used in the communication (e.g., {@code TCP}, {@code UDP}, {@code ICMP}).</li>
 *   <li><strong>rawMessage</strong> – Raw IDS message payload as received from the detection engine, useful for logging or forensic analysis.</li>
 * </ul>
 *
 * <p>This payload supports real-time threat monitoring, forensic analysis, and automated response workflows.</p>
 */
public record IdsPayload(
        AttackType attackType,
        String sourceIp,
        String destinationIp,
        int sourcePort,
        int destinationPort,
        String protocol,
        String rawMessage
) {
}
