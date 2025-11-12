package com.youssef.GridPulse.domain.inverter.inverter.entity;


import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class InverterHistory extends BaseHistoryEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private String version;
    @Column(nullable = false)
    private String manufacturer;

    @ElementCollection
    private List<UUID> deviceIds;
}
