package com.youssef.GridPulse.domain.bms.entity;

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
public class BmsHistory extends BaseHistoryEntity {
    private String name;
    private String model;
    private String manufacturer;
    private String batteryChemistry; // e.g., Li-ion, NiMH, Lead-Acid
    private Float soc; // State of Charge (0.0 - 100.0)
    private String soh; // State of Health e.g., Good, Fair, Poor
    private String version; // Firmware or software version
}
