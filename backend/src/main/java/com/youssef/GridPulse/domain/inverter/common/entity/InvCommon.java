package com.youssef.GridPulse.domain.inverter.common.entity;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * SunSpec Common Model (Model ID 1)
 * Fields use idiomatic camelCase names while column names keep snake_case DB mapping.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class InvCommon extends SunSpecModelEntity {

    @Builder.Default
    private final int modelId = 1;

    /**
     * Manufacturer (SunSpec well-known value)
     * JSON name: "Mn"
     */
    @Column
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