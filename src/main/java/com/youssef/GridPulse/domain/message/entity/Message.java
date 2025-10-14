package com.youssef.GridPulse.domain.message.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.message.dto.DevicePayload;
import com.youssef.GridPulse.domain.message.enums.*;
import com.youssef.GridPulse.domain.message.parser.MessagePayloadParser;

import com.youssef.util.SeverityInterpreter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

/**
 * Represents a message sent from an IoT device, including type, severity, payload, and metadata.
 *
 * <p>This entity models communication from devices across seven distinct message types:</p>
 * <ul>
 *   <li><b>IDS</b>: Intrusion Detection System alerts</li>
 *   <li><b>METER</b>: Metering data and readings</li>
 *   <li><b>BMS</b>: Battery Management System updates</li>
 *   <li><b>INVERTER</b>: Inverter status and telemetry</li>
 *   <li><b>HEARTBEAT</b>: Periodic signals confirming device is online</li>
 *   <li><b>SOFTWARE</b>: Software update notifications and diagnostics</li>
 *   <li><b>SYSTEM</b>: General system-level messages</li>
 * </ul>
 *
 * <p>Each message includes a severity level—<code>INFO</code>, <code>LOW</code>, <code>MEDIUM</code>, or <code>CRITICAL</code>—
 * determined by the message type and its content. Severity logic is handled by {@link SeverityInterpreter}.</p>
 *
 * <p>Payloads are typically JSON objects, base64-encoded for transmission integrity.</p>
 *
 * <p>Messages are linked to devices via a many-to-one relationship with {@link Device}, allowing multiple messages
 * to be associated with a single device instance.</p>
 *
 * <p>Additional metadata includes:</p>
 * <ul>
 *   <li>Message format: {@link MessageFormat} (e.g., TEXT, HEX)</li>
 *   <li>Message priority: {@link MessagePriority} (LOW, MEDIUM, HIGH)</li>
 *   <li>Message status: {@link MessageStatus} (NEW, READY, COMPLETE, etc.)</li>
 * </ul>
 *
 * <p>Use the static factory method {@link #fromDevicePayload(Device, String)} to create a Message instance from
 * a raw device payload. This method handles base64 decoding, JSON parsing, and field population.</p>
 *
 * <p>This is a JPA entity mapped to the <code>messages</code> table.</p>
 *
 * @see Device
 * @see MessageType
 * @see Severity
 * @see SeverityInterpreter
 * @see MessageFormat
 * @see MessagePriority
 * @see MessageStatus
 */

@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Message extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    private UUID fleetId;

    @Column(unique = true)
    private String cloudMessageId;

    @Column(columnDefinition = "TEXT")
    private String messageText;

    private Instant receivedAt;

    private Instant sentAt;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Enumerated(EnumType.STRING)
    private MessageFormat format;

    @Enumerated(EnumType.STRING)
    private MessagePriority priority;

    private String explanation;

    /**
     * Basic factory method to transform a payload device (format:
     * Message_ID/Message_B64/Date/Priority/Format/Status|IDS) in Message entity.
     * Implement the actual parsing according to the exact format and encoding.
     */
    public static Message fromDevicePayload(Device device, String rawPayload) {
        DevicePayload dto = DevicePayload.fromRaw(rawPayload);

        MessageType type = MessageType.valueOf(dto.type().toUpperCase());
        String decodedJson = new String(Base64.getDecoder().decode(dto.base64Message()));
        Object parsedPayload = MessagePayloadParser.parse(type, dto.base64Message());

        Severity severity = SeverityInterpreter.calculate(type, parsedPayload);
        String explanation = SeverityInterpreter.explain(severity, type);

        return Message.builder()
                .device(device)
                .cloudMessageId(dto.messageId())
                .messageText(decodedJson)
                .sentAt(Instant.now())
                .receivedAt(dto.date())
                .severity(severity)
                .explanation(explanation)
                .priority(MessagePriority.valueOf(dto.priority().toUpperCase()))
                .format(MessageFormat.valueOf(dto.format().toUpperCase()))
                .messageType(type)
                .status(MessageStatus.valueOf(dto.status().toUpperCase()))
                .build();
    }


}
