package com.youssef.GridPulse.domain.device.repository;

import com.youssef.GridPulse.domain.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByFleetId(UUID fleetId);
}
