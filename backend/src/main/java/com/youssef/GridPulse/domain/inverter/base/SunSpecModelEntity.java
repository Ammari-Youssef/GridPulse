package com.youssef.GridPulse.domain.inverter.base;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class SunSpecModelEntity extends BaseEntity {

    /**
     * SunSpec model identifier (e.g., 1 for InvCommon, 120 for InvNameplate .etc)
     * JSON name: "ID"
     */
    @Column(name = "model_id", nullable = false)
    protected int modelId; // SunSpec model ID

    /**
     * Model length
     * JSON name: "L"
     */
    @Column(name = "model_length", nullable = false)
    @Builder.Default
    protected int modelLength = 0; // SunSpec model length (L)

    /**
     * Inverter relationship
     * JSON name: inverterId
     */
    @ManyToOne
    @JoinColumn(name = "inverter_id", nullable = false)
    protected Inverter inverter;

}

