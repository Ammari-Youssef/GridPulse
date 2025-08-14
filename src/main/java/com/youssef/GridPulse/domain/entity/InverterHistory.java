package com.youssef.GridPulse.domain.entity;


import com.youssef.GridPulse.domain.base.BaseHistoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
}
