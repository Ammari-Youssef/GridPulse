package com.youssef.GridPulse.domain.inverter.entity;

import com.youssef.GridPulse.domain.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Inverter extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String version;
    @Column(nullable = false)
    private String manufacturer;

    @OneToMany(mappedBy = "inverter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;

}
