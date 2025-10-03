package com.youssef.GridPulse.domain.message.entity;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class MessageHistory extends BaseHistoryEntity {

    @Column(nullable = false)
    private UUID deviceId;

    private UUID fleetId;

    private String cloudMessageId;

    @Column(columnDefinition = "TEXT")
    private String messageText;

    private Instant receivedAt;

    private Instant sentAt;

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public enum Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public enum MessageType {
        ALERT, STATUS, COMMAND, TELEMETRY
    }
}
