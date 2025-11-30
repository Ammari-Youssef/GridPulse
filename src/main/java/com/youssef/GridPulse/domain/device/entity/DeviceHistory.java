package com.youssef.GridPulse.domain.device.entity;

import com.youssef.GridPulse.common.base.BaseHistoryEntity;
import com.youssef.GridPulse.domain.device.enums.DeviceStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class DeviceHistory extends BaseHistoryEntity {

    private String name;
    private String model;
    private String softwareVersion;
    private String manufacturer;
    private String serialNumber;

    private String ip;
    private Double gpsLat;
    private Double gpsLong;
    private DeviceStatus status;
    private OffsetDateTime lastSeen;
    private OffsetDateTime swUpdateTime;

    private UUID userId;
    private UUID operatorId;
    private UUID inverterId;
    private UUID bmsId;
    private UUID meterId;
    private UUID fleetId;

}