package com.youssef.GridPulse.domain.inverter.entity;

import com.youssef.GridPulse.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

}
