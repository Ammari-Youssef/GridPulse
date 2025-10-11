package com.youssef.GridPulse.domain.message.entity;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import com.youssef.GridPulse.domain.message.enums.*;
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

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @Enumerated(EnumType.STRING)
    private MessageFormat format;

    @Enumerated(EnumType.STRING)
    private MessagePriority priority;

    private String explanation;
}
