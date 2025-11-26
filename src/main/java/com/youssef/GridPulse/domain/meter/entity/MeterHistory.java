package com.youssef.GridPulse.domain.meter.entity;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class MeterHistory extends BaseHistoryEntity {

    private String name;
    private String model;
    private String manufacturer;
    private String version; // Firmware or software version

    private Float energyConsumed;
    private Float energyProduced;
    private Float powerDispatched; // Can be negative or positive
}
