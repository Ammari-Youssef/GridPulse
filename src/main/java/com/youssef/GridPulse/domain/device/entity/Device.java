package com.youssef.GridPulse.domain.device.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.device.enums.DeviceStatus;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Device extends BaseEntity {

    private Float soc; // State of Charge (0.0 - 100.0)
    private Float soh; // State of Health (%) e.g., "98%"
    private String batteryChemistry;
    private Integer cycles;
    private Double gpsLat;
    private Double gpsLong;
    private Instant lastSeen;
    private String name;
    private Float powerDispatched; // Can be negative or positive
    @Enumerated(EnumType.STRING)
    private DeviceStatus status;
    private Float temperature;
    private Float voltage;
    private String model;
    private String manufacturer;
    private String softwareVersion;

    private Instant swUpdateTime; // Time of last software update
    private String ip; // IPv4 or IPv6 address

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", referencedColumnName = "id", nullable = false)
    private User operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inverter_id", referencedColumnName = "id", nullable = false)
    private Inverter inverter;

    @OneToMany(mappedBy = "device", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Message> messages;

    @OneToOne
    @JoinColumn(name = "bms_id")
    private Bms bms;

    @OneToOne
    @JoinColumn(name = "meter_id")
    private Meter meter;

}
