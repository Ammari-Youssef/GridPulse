package com.youssef.GridPulse.common.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseHistoryEntity extends BaseEntity{

    @Column(nullable = false)
    private UUID originalId; // Links back to the main entity

    @Builder.Default
    private boolean createdRecord = false;
    @Builder.Default
    private boolean updatedRecord = false;
    @Builder.Default
    private boolean deletedRecord = false;
    @Builder.Default
    private boolean synced = false;


}
