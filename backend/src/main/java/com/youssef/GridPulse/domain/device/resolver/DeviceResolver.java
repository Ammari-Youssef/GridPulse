package com.youssef.GridPulse.domain.device.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageRequestInput;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageResponse;
import com.youssef.GridPulse.domain.device.dto.DeviceInput;
import com.youssef.GridPulse.domain.device.dto.DeviceStats;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.entity.DeviceHistory;
import com.youssef.GridPulse.domain.bms.enums.BatteryHealthStatus;
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

    // Device Stats
    @QueryMapping
    public DeviceStats getDeviceStats() {
        return service.getDeviceStats();
    }

    // Assign Device to User
    @MutationMapping
    public Device assignDeviceToUser(@Argument UUID deviceId, @Argument UUID userId){
        return service.assignDeviceToUser(deviceId, userId);
    }
    // Assign Device to Operator
    @MutationMapping
    public Device assignDeviceToOperator(@Argument UUID deviceId, @Argument UUID operatorId){
        return service.assignDeviceToOperator(deviceId, operatorId);
    }

    // Pagination Offset
    @QueryMapping
    public PageResponse<Device> getAllDevicePaged(@Argument PageRequestInput pageRequest) {
        return super.getAllPaged(pageRequest);
    }

    @QueryMapping
    public PageResponse<DeviceHistory> getAllDeviceHistoryPaged(@Argument PageRequestInput pageRequest) {
        return super.getAllHistoryPaged(pageRequest);
    }

    @QueryMapping
    public PageResponse<DeviceHistory> getDeviceHistoryByOriginalIdPaged(@Argument UUID originalId, @Argument PageRequestInput pageRequest) {
        return super.getHistoryByOriginalIdPaged(originalId, pageRequest);
    }

    // Pagination Offset - by Fleet ID (Special)
    @QueryMapping
    public PageResponse<Device> getDeviceByFleetIdPaged(@Argument UUID fleetId, @Argument PageRequestInput pageRequest) {
        return service.getByFleetIdOffsetBased(fleetId, pageRequest);
    }

    // Pagination Offset - by Operator ID (Special)
    @QueryMapping
    public PageResponse<Device> getDevicesByOperatorIdPaged(@Argument UUID operatorId, @Argument PageRequestInput pageRequest) {
        return service.getByOperatorIdOffsetBased(operatorId, pageRequest);
    }

    // Pagination Offset - by User ID (Special)
    @QueryMapping
    public PageResponse<Device> getDevicesByUserIdPaged(@Argument UUID userId, @Argument PageRequestInput pageRequest) {
        return service.getByUserIdOffsetBased(userId, pageRequest);
    }

    // Listing - By Operator ID (Special)
    @QueryMapping
    public List<Device> getDevicesByOperatorId(@Argument UUID operatorId) {
        return service.getByOperatorId(operatorId);
    }
    // Listing - By User ID
    @QueryMapping
    public List<Device> getDevicesByUserId(@Argument UUID userId) {
        return service.getByUserId(userId);
    }

    // Listing - By Fleet ID
    @QueryMapping
    public List<Device> getDevicesByFleetId(@Argument UUID fleetId) {
        return service.getByFleetId(fleetId);
    }

    @QueryMapping
    public BatteryHealthStatus getHealthStatus(@Argument UUID deviceId) {
        return service.getHealthStatus(deviceId);
    }

    // Common CRUD
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
