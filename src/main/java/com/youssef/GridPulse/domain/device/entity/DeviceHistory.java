package com.youssef.GridPulse.domain.device.entity;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class DeviceHistory extends BaseHistoryEntity {

    private Float soc;
    private Float soh;
    private String batteryChemistry;
    private Integer cycles;
    private Double gpsLat;
    private Double gpsLong;
    private Instant lastSeen;
    private String name;
    private Float powerDispatched;
    private String status;
    private Float temperature;
    private Float voltage;
    private String model;
    private String manufacturer;
    private String softwareVersion;
    private Instant swUpdateTime;
    private String ip;

    private UUID userId;
    private UUID operatorId;
    private UUID inverterId;
    private UUID bmsId;
    private UUID meterId;
    private UUID fleetId;

}