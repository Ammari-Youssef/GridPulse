package com.youssef.GridPulse.domain.message.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
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

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public enum MessageType {
        ALERT, STATUS, COMMAND, TELEMETRY
    }
}
