package com.youssef.GridPulse.domain.device.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageRequestInput;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageResponse;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.bms.repository.BmsRepository;
import com.youssef.GridPulse.domain.device.dto.DeviceInput;
import com.youssef.GridPulse.domain.device.dto.DeviceStats;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.entity.DeviceHistory;
import com.youssef.GridPulse.domain.bms.enums.BatteryHealthStatus;
import com.youssef.GridPulse.domain.device.enums.DeviceStatus;
import com.youssef.GridPulse.domain.device.mapper.DeviceMapper;
import com.youssef.GridPulse.domain.device.repository.DeviceHistoryRepository;
import com.youssef.GridPulse.domain.device.repository.DeviceRepository;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.fleet.repository.FleetRepository;
import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import com.youssef.GridPulse.domain.message.enums.MessageStatus;
import com.youssef.GridPulse.domain.message.enums.Severity;
import com.youssef.GridPulse.domain.message.repository.MessageRepository;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.domain.meter.repository.MeterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService extends BaseService<Device, DeviceHistory, UUID, DeviceInput> {

    private final UserRepository userRepository;
    private final InverterRepository inverterRepository;
    private final BmsRepository bmsRepository;
    private final MeterRepository meterRepository;
    private final FleetRepository fleetRepository;

    // MessageRepository for Device Stats
    private final MessageRepository messageRepository;


    public DeviceService(
            DeviceRepository repository,
            DeviceHistoryRepository historyRepository,
            DeviceMapper mapper,


            UserRepository userRepository,
            InverterRepository inverterRepository,
            BmsRepository bmsRepository,
            MeterRepository meterRepository,
            FleetRepository fleetRepository,

            MessageRepository messageRepository) {
        super(repository, historyRepository, mapper);
        this.userRepository = userRepository;
        this.inverterRepository = inverterRepository;
        this.bmsRepository = bmsRepository;
        this.meterRepository = meterRepository;
        this.fleetRepository = fleetRepository;

        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    protected void beforeSave(Device entity, DeviceInput input) {
        // Resolve relations
        User user = userRepository.findById(input.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        User operator = userRepository.findById(input.operatorId())
                .orElseThrow(() -> new EntityNotFoundException("Operator not found"));

        Inverter inverter = inverterRepository.findById(input.inverterId())
                .orElseThrow(() -> new EntityNotFoundException("Inverter not found"));

        Bms bms = bmsRepository.findById(input.bmsId())
                .orElseThrow(() -> new EntityNotFoundException("BMS not found"));

        Meter meter = meterRepository.findById(input.meterId())
                .orElseThrow(() -> new EntityNotFoundException("Meter not found"));

        Fleet fleet = fleetRepository.findById(input.fleetId())
                .orElseThrow(() -> new EntityNotFoundException("Fleet not found"));

        // enforce role constraint
        if (operator.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Operator must have ADMIN role");
        }

        entity.setUser(user);
        entity.setOperator(operator);
        entity.setInverter(inverter);
        entity.setBms(bms);
        entity.setMeter(meter);
        entity.setFleet(fleet);
    }

    @Transactional
    @Override
    protected void setRelationsInHistory(DeviceHistory history, Device entity) {
        history.setUserId(entity.getUser().getId());
        history.setOperatorId(entity.getOperator().getId());
        history.setInverterId(entity.getInverter().getId());
        history.setBmsId(entity.getBms().getId());
        history.setMeterId(entity.getMeter().getId());
        history.setFleetId(entity.getFleet().getId());
    }

    public BatteryHealthStatus getHealthStatus(UUID deviceId) {
        Device device = repository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        return device.getBms().getSoh();
    }

    public List<Device> getByFleetId(UUID fleetId) {
        return ((DeviceRepository) repository).findByFleetId(fleetId);
    }

    public List<Device> getByUserId(UUID userId) {
        return ((DeviceRepository) repository).findByUserId(userId);
    }

    public List<Device> getByOperatorId(UUID operatorId) {
        return ((DeviceRepository) repository).findByOperatorId(operatorId);
    }

    public PageResponse<Device> getByFleetIdOffsetBased(UUID fleetId, PageRequestInput pageRequest) {
        Pageable pageable = setPageRequestFields(pageRequest);
        Page<Device> result = ((DeviceRepository) repository).findByFleet_Id(fleetId, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    public PageResponse<Device> getByUserIdOffsetBased(UUID userId, PageRequestInput pageRequest) {
        Pageable pageable = setPageRequestFields(pageRequest);
        Page<Device> result = ((DeviceRepository) repository).findByUser_Id(userId, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    public PageResponse<Device> getByOperatorIdOffsetBased(UUID operatorId, PageRequestInput pageRequest) {
        Pageable pageable = setPageRequestFields(pageRequest);
        Page<Device> result = ((DeviceRepository) repository).findByOperator_Id(operatorId, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    @Transactional
    public Device assignDeviceToOperator(UUID deviceId, UUID operatorId) {
        Device device = ((DeviceRepository) repository).findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        User operator = userRepository.findById(operatorId)
                .orElseThrow(() -> new EntityNotFoundException("Operator not found"));

        device.setOperator(operator);
        return ((DeviceRepository) repository).save(device);
    }

    @Transactional
    public Device assignDeviceToUser(UUID deviceId, UUID userId) {
        Device device = ((DeviceRepository) repository).findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

//        if (device.getUser() != null) {
//            throw new IllegalStateException("Device already assigned to an end user");
//        }

        device.setUser(user);
        return ((DeviceRepository) repository).save(device);
    }

    // Device Stats

    @Transactional(readOnly = true)
    public DeviceStats getDeviceStats() {
        Long total = ((DeviceRepository) repository).count();
        Long online = ((DeviceRepository) repository).countByStatus(DeviceStatus.ONLINE);
        Long offline = ((DeviceRepository) repository).countByStatus(DeviceStatus.OFFLINE);
        Long maintenance = ((DeviceRepository) repository).countByStatus(DeviceStatus.MAINTENANCE);

        // All NEW messages
        Long totalAlerts = messageRepository.countByStatus(MessageStatus.NEW);

        // Only CRITICAL NEW messages
        Long criticalAlerts = messageRepository.countByStatusAndSeverity(
                MessageStatus.NEW,
                Severity.CRITICAL
        );

        return DeviceStats.builder()
                .totalDevices(total.intValue())
                .onlineDevices(online.intValue())
                .offlineDevices(offline.intValue())
                .maintenanceDevices(maintenance.intValue())
                .totalAlerts(totalAlerts.intValue())
                .criticalAlerts(criticalAlerts.intValue())

                .build();
    }

}
