package com.youssef.GridPulse.domain.security.entity;


import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import com.youssef.GridPulse.domain.security.enums.KeySource;
import com.youssef.GridPulse.domain.security.enums.KeyStatus;
import com.youssef.GridPulse.domain.security.enums.SecurityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "security_keys_history", indexes = {@Index(name = "idx_sk_history_original_id", columnList = "original_id")})
public class SecurityKeyHistory extends BaseHistoryEntity {

    private String name;

    private UUID deviceId;

    private UUID ownerId; // The user who generated/uploaded the key

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private SecurityType securityType;

    private String serialNumber;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String publicKey;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String privateKey;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private KeySource keySource;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private KeyStatus status;

    private Instant revokedTimestamp;
}