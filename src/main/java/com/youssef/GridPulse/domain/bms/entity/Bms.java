package com.youssef.GridPulse.domain.bms.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import jakarta.persistence.Entity;
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
    private String batteryChemistry; // e.g., Li-ion, NiMH, Lead-Acid
    private Float soc; // State of Charge (0.0 - 100.0)
    private String soh; // State of Health e.g., Good, Fair, Poor
    private String version; // Firmware or software version

    @OneToOne(mappedBy = "bms")
    private Device device;


}
