package com.youssef.GridPulse.domain.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
