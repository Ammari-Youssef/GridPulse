package com.youssef.GridPulse.domain.security.entity;


import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.security.enums.KeySource;
import com.youssef.GridPulse.domain.security.enums.KeyStatus;
import com.youssef.GridPulse.domain.security.enums.SecurityType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

/**
 * Represents a cryptographic key pair used for secure communication,
 * authentication, and encryption across system components.
 *
 * <p>This entity stores both public and private keys, scoped to a specific fleet.
 * Private keys must be encrypted or securely managed via external vaults.</p>
 *
 * @see BaseEntity for audit metadata (createdAt, updatedAt, source, etc.)
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "security_keys")
public class SecurityKey extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id")
    private Device device; // Fleet can be reached via device.getFleet()

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner; // The user who generated/uploaded the key

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SecurityType securityType; // SOFTWARE_KEY, SOFT, TPM

    @Column(unique = true, nullable = false)
    private String serialNumber;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String publicKey;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String privateKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private KeySource keySource; // CLOUD, DEVICE, GATEWAY

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private KeyStatus status = KeyStatus.ACTIVE;

    private OffsetDateTime revokedTimestamp;
}