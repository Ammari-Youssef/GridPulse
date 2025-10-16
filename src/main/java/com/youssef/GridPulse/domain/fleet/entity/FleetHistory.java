package com.youssef.GridPulse.domain.fleet.entity;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import jakarta.persistence.Entity;

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
public class FleetHistory extends BaseHistoryEntity {

    private String name;

    private String location;

    private String owner; // optional: customer, department, etc.

    private String description;

}

