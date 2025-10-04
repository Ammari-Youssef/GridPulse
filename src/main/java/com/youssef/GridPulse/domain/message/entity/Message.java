package com.youssef.GridPulse.domain.message.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.message.enums.MessageStatus;
import com.youssef.GridPulse.domain.message.enums.MessageType;
import com.youssef.GridPulse.domain.message.enums.Severity;
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

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

}
