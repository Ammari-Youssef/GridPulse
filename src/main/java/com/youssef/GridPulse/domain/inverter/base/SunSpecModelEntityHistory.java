package com.youssef.GridPulse.domain.inverter.base;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class SunSpecModelEntityHistory extends BaseHistoryEntity {

    /**
     * SunSpec model identifier (fixed value 1)
     * JSON name: "ID"
     */
    @Column(name = "model_id", nullable = false)
    protected int modelId; // SunSpec model ID

    /**
     * Model length
     * JSON name: "L"
     */
    @Column(name = "model_length")
    @Builder.Default
    protected int modelLength = 0; // SunSpec model length (L)

    /**
     * Inverter id
     * JSON name: "inverterId
     */
    protected UUID inverterId;
}

