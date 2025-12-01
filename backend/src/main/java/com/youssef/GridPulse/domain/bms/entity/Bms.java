package com.youssef.GridPulse.domain.bms.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.bms.enums.BatteryChemistry;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.bms.enums.BatteryHealthStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
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
public class Bms extends BaseEntity {

    private String name;
    private String model;
    private String manufacturer;
    private String version; // Firmware or software version

    private Float soc; // State of Charge (0.0 - 100.0)
    @Enumerated(EnumType.STRING)
    private BatteryHealthStatus soh; // State of Health (%)
    @Enumerated(EnumType.STRING)
    private BatteryChemistry batteryChemistry; // Lithium-Ion, Lead-Acid, etc.
    private Integer cycles;
    private Float temperature;
    private Float voltage;


    @OneToOne(mappedBy = "bms")
    private Device device;
}

