package com.youssef.GridPulse.domain.inverter.settings.entity;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelEntity;
import com.youssef.GridPulse.domain.inverter.settings.enums.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * SunSpec Basic Settings Model (Model ID 121)
 * Inverter Controls Basic Settings
 * Fields use idiomatic camelCase names while column names keep snake_case DB mapping.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class InvSettings extends SunSpecModelEntity {

    /**
     * SunSpec model identifier (fixed value 121)
     * JSON name: "ID"
     */
    @Builder.Default
    @Column(nullable = false)
    private final int modelId = 121;

    /**
     * Model length
     * JSON name: "L"
     */
    @Column
    private int modelLength;

    /**
     * Maximum power output (W)
     * JSON name: "WMax"
     */
    @Column
    private Integer wMax;

    /**
     * Voltage reference at PCC (V)
     * JSON name: "VRef"
     */
    @Column
    private Integer vRef;

    /**
     * Voltage offset from PCC to inverter (V)
     * JSON name: "VRefOfs"
     */
    @Column
    private Integer vRefOfs;

    /**
     * Maximum voltage setpoint (V)
     * JSON name: "VMax"
     */
    @Column
    private Integer vMax;

    /**
     * Minimum voltage setpoint (V)
     * JSON name: "VMin"
     */
    @Column
    private Integer vMin;

    /**
     * Maximum apparent power (VA)
     * JSON name: "VAMax"
     */
    @Column
    private Integer vaMax;

    /**
     * Maximum reactive power in quadrant 1 (var)
     * JSON name: "VArMaxQ1"
     */
    @Column
    private Integer varMaxQ1;

    /**
     * Maximum reactive power in quadrant 2 (var)
     * JSON name: "VArMaxQ2"
     */
    @Column
    private Integer varMaxQ2;

    /**
     * Maximum reactive power in quadrant 3 (var)
     * JSON name: "VArMaxQ3"
     */
    @Column
    private Integer varMaxQ3;

    /**
     * Maximum reactive power in quadrant 4 (var)
     * JSON name: "VArMaxQ4"
     */
    @Column
    private Integer varMaxQ4;

    /**
     * Ramp rate of active power (% WMax/sec)
     * JSON name: "WGra"
     */
    @Column
    private Integer wGra;

    /**
     * Minimum power factor in quadrant 1
     * JSON name: "PFMinQ1"
     */
    @Column
    private Integer pfMinQ1;

    /**
     * Minimum power factor in quadrant 2
     * JSON name: "PFMinQ2"
     */
    @Column
    private Integer pfMinQ2;

    /**
     * Minimum power factor in quadrant 3
     * JSON name: "PFMinQ3"
     */
    @Column
    private Integer pfMinQ3;

    /**
     * Minimum power factor in quadrant 4
     * JSON name: "PFMinQ4"
     */
    @Column
    private Integer pfMinQ4;

    /**
     * VAR action on charging/discharging transition
     * JSON name: "VArAct"
     */
    @Enumerated(EnumType.STRING)
    @Column
    private VarAction varAct;

    /**
     * Apparent power calculation method
     * JSON name: "ClcTotVA"
     */
    @Enumerated(EnumType.STRING)
    @Column
    private ClcTotVaMethod clcTotVa;

    /**
     * Maximum ramp rate percentage
     * JSON name: "MaxRmpRte"
     */
    @Column
    private Integer maxRmpRte;

    /**
     * Nominal frequency at ECP (Hz)
     * JSON name: "ECPNomHz"
     */
    @Column
    private Integer ecpNomHz;

    /**
     * Connected phase identity (A=1, B=2, C=3)
     * JSON name: "ConnPh"
     */
    @Enumerated(EnumType.STRING)
    @Column
    private ConnPhase connPh;
}
