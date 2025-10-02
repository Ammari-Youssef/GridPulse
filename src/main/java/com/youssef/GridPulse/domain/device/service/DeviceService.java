package com.youssef.GridPulse.domain.device.service;

import com.youssef.GridPulse.domain.base.BaseService;
import com.youssef.GridPulse.domain.device.dto.DeviceInput;
import com.youssef.GridPulse.domain.device.entity.Device;
import com.youssef.GridPulse.domain.device.entity.DeviceHistory;
import com.youssef.GridPulse.domain.device.mapper.DeviceMapper;
import com.youssef.GridPulse.domain.device.repository.DeviceHistoryRepository;
import com.youssef.GridPulse.domain.device.repository.DeviceRepository;
import com.youssef.GridPulse.domain.identity.user.Role;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.repository.UserRepository;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.repository.InverterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeviceService extends BaseService<Device, DeviceHistory, UUID, DeviceInput> {

    private final UserRepository userRepository;
    private final InverterRepository inverterRepository;

    public DeviceService(
            DeviceRepository repository,
            DeviceHistoryRepository historyRepository,
            DeviceMapper mapper,


            UserRepository userRepository,
            InverterRepository inverterRepository
    ) {
        super(repository, historyRepository, mapper);
        this.userRepository = userRepository;
        this.inverterRepository = inverterRepository;
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

        // enforce role constraint
        if (operator.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Operator must have ADMIN role");
        }

        entity.setUser(user);
        entity.setOperator(operator);
        entity.setInverter(inverter);
    }

    @Transactional
    @Override
    protected void setRelationsInHistory(DeviceHistory history, Device entity) {
        history.setUserId(entity.getUser().getId());
        history.setOperatorId(entity.getOperator().getId());
        history.setInverterId(entity.getInverter().getId());
    }

}
