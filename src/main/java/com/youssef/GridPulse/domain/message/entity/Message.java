package com.youssef.GridPulse.domain.message.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vladmihalcea.hibernate.type.json.JsonType;
import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.message.enums.*;
import com.youssef.GridPulse.domain.message.parser.MessagePayloadParser;
import com.youssef.GridPulse.domain.message.util.SeverityInterpreter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;

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
public class Message extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(unique = true)
    private String cloudMessageId;

    @Type(JsonType.class)
    @JdbcTypeCode(SqlTypes.JSON) // For Hibernate 6+
    @Column(columnDefinition = "jsonb")
    private Object messageText;

    private OffsetDateTime receivedAt;

    private OffsetDateTime sentAt;

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

    @Transient
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @PostLoad
    public void hydrateMessageText() {
        if (messageText instanceof LinkedHashMap && messageType != null) {
            JsonNode jsonNode = objectMapper.valueToTree(messageText);
            this.messageText = MessagePayloadParser.parse(messageType, jsonNode);
        }
    }

}
