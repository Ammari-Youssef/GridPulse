package com.youssef.GridPulse.domain.inverter.inverter.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
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

    @OneToMany(mappedBy = "inverter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvCommon> commonList;

    @OneToMany(mappedBy = "inverter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvSettings> settingsList;

    @OneToMany(mappedBy = "inverter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvNameplate> nameplateList;

}
