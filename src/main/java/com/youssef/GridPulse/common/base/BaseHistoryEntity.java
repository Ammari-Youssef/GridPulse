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

    private boolean createdRecord = false;
    private boolean updatedRecord = false;
    private boolean deletedRecord = false;
    private boolean synced = false;

}
