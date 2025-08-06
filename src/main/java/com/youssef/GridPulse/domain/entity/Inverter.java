package com.youssef.GridPulse.domain.entity;

import com.youssef.GridPulse.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
