package com.youssef.GridPulse.domain.fleet.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.device.entity.Device;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Fleet extends BaseEntity {

    private String name;

    private String location;

    private String owner; // customer, department, etc.

    private String description;

    @OneToMany(mappedBy = "fleet", fetch = FetchType.LAZY)
    private List<Device> devices;
}

