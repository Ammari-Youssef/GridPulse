package com.youssef.GridPulse.domain.device.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;
import com.youssef.GridPulse.domain.device.dto.DeviceInput;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.entity.DeviceHistory;
import com.youssef.GridPulse.domain.enums.BatteryHealthStatus;
import com.youssef.GridPulse.domain.device.service.DeviceService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class DeviceResolver extends BaseResolver<Device, DeviceHistory, UUID, DeviceInput> {

    private final DeviceService service;

    public DeviceResolver(DeviceService service) {
        super(service);
        this.service = service;
    }

    @QueryMapping
    public BatteryHealthStatus getHealthStatus(@Argument UUID deviceId) {
        return service.getHealthStatus(deviceId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Device> getAllDevices() {
        return super.getAll();
    }

    @QueryMapping
    public Device getDeviceById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Device createDevice(@Argument DeviceInput input) {
        return service.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Device updateDevice(@Argument UUID id, @Argument DeviceInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteDevice(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<DeviceHistory> getDeviceHistory(@Argument UUID originalId) {
        return super.service.findHistoryByOriginalId(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<DeviceHistory> getAllDeviceHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public DeviceHistory getDeviceHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markDeviceHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }

}
