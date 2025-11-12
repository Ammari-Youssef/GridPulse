package com.youssef.GridPulse.domain.inverter.common.entity;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelEntityHistory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(indexes = {@Index(name = "idx_invCommon_history_original_id", columnList = "original_id")})
public class InvCommonHistory extends SunSpecModelEntityHistory {

    /**
     * SunSpec model identifier (fixed value 1)
     * JSON name: "ID"
     */
    @Builder.Default
    @Column(nullable = false)
    private final int modelId = 1;

    /**
     * Model length
     * JSON name: "L"
     */
    @Builder.Default
    private int modelLength = 0;

    /**
     * Manufacturer (SunSpec well-known value)
     * JSON name: "Mn"
     */
    private String manufacturer;

    /**
     * Model (manufacturer specific)
     * JSON name: "Md"
     */
    @Column
    private String model;

    /**
     * Options (manufacturer specific)
     * JSON name: "Opt"
     */
    @Column
    private String options;

    /**
     * Version (manufacturer specific)
     * JSON name: "Vr"
     */
    @Column
    private String version;

    /**
     * Serial Number (manufacturer specific)
     * JSON name: "SN"
     */
    @Column
    private String serialNumber;

    /**
     * Modbus device address (read-write)
     * JSON name: "DA"
     */
    @Column
    private Integer deviceAddress;

    /**
     * Padding for alignment (pad)
     * JSON name: "Pad"
     */
    @Column
    private Integer pad;

}