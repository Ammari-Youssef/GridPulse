package com.youssef.GridPulse.domain.message.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.message.dto.DevicePayload;
import com.youssef.GridPulse.domain.message.enums.*;
import com.youssef.GridPulse.domain.message.parser.MessagePayloadParser;

import com.youssef.GridPulse.common.util.SeverityInterpreter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

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
