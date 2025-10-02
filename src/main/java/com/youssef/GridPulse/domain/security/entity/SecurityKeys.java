package com.youssef.GridPulse.domain.security.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

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
public class SecurityKeys extends BaseEntity {

    /**
     * UUID of the fleet or logical group this key belongs to.
     * Enables multi-tenant key scoping and isolation.
     */
    @Column(nullable = false)
    private UUID fleetId;

    /**
     * Type of security credential.
     * Examples: "API_KEY", "JWT_SIGNING", "SSL_CERT", "SSH_KEY", "DATA_ENCRYPTION", "PGP_KEY".
     */
    @Column(nullable = false)
    private String securityType;

    /**
     * Unique identifier or serial number for the key or certificate.
     * Used for tracking, rotation, and revocation.
     */
    @Column(unique = true, nullable = false)
    private String serialNumber;

    /**
     * Public key material in PEM or Base64 format.
     * Used for encryption and signature verification.
     */
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String publicKey;

    /**
     * Encrypted private key material.
     * Used for decryption and signing. Never store plaintext keys.
     * Recommended: Jasypt, AWS KMS, HashiCorp Vault, or similar.
     */
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String privateKey;
}
