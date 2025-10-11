package com.youssef.GridPulse.common.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private Instant updatedAt;

    @CreatedBy
    @Builder.Default
    @Column(updatable = false, nullable = false)
    private String createdBy = "system" ; // default value can be either 'system' or 'app'

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private Source source = Source.APP; // default value can be either 'app' or 'sync'
}
