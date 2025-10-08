package com.youssef.GridPulse.domain.meter.entity;

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
public class Meter extends BaseEntity {

    private String name;
    private String model;
    private String manufacturer;
    private String version; // Firmware or software version

    @OneToOne(mappedBy = "meter")
    private Device device;

}
