package com.youssef.GridPulse.domain.device.repository;

import com.youssef.GridPulse.common.base.BaseRepository;
import com.youssef.GridPulse.domain.device.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends BaseRepository<Device, UUID> {
    List<Device> findByFleetId(UUID fleetId);
    Page<Device> findByFleet_Id(UUID fleetId, Pageable pageable);
}
