package com.youssef.GridPulse.domain.device.entity;

import com.youssef.GridPulse.common.base.BaseEntity;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.device.enums.DeviceStatus;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

import com.youssef.GridPulse.domain.enums.BatteryHealthStatus;

import static com.youssef.GridPulse.domain.enums.BatteryHealthStatus.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Device extends BaseEntity {

    // --- Identity & Metadata ---
    @Column(nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private String name;

    @Column
    private String model;

    @Column
    private String manufacturer;

    // --- Status & Lifecycle ---
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DeviceStatus status;

    private Instant lastSeen;

    // --- Battery & Power ---
    private Float soc; // State of Charge (0.0 - 100.0)
    private Float soh; // State of Health (%)
    private String batteryChemistry;
    private Integer cycles;
    private Float powerDispatched; // Can be negative or positive
    private Float temperature;
    private Float voltage;

    // --- Software & Connectivity ---
    private String softwareVersion;
    private Instant swUpdateTime; // Last software update
    private String ip; // IPv4 or IPv6

    // --- Location ---
    private Double gpsLat;
    private Double gpsLong;


    // --- Relationships ---
    @OneToOne
    @JoinColumn(name = "bms_id")
    private Bms bms;

    @OneToOne
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id", referencedColumnName = "id", nullable = false)
    private User operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inverter_id", referencedColumnName = "id", nullable = false)
    private Inverter inverter;

    @ManyToOne
    @JoinColumn(name = "fleet_id", referencedColumnName = "id")
    private Fleet fleet;

    @OneToMany(mappedBy = "device", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Message> messages;

    @OneToMany(mappedBy = "device")
    private List<SecurityKey> securityKeys;


    @Transient
    public BatteryHealthStatus getHealthStatus() {
        if (soh == null || soh < 0 || soh > 100) {
            return UNKNOWN;
        }
        return soh < 20 ? CRITICAL :
                soh < 30 ? DEGRADED :
                        soh < 50 ? UNHEALTHY :
                                HEALTHY;
    }

}
