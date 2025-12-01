package com.youssef.GridPulse.domain.inverter.common.service;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelService;
import com.youssef.GridPulse.domain.inverter.common.dto.InvCommonInput;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommonHistory;
import com.youssef.GridPulse.domain.inverter.common.mapper.InvCommonMapper;
import com.youssef.GridPulse.domain.inverter.common.repository.InvCommonHistoryRepository;
import com.youssef.GridPulse.domain.inverter.common.repository.InvCommonRepository;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvCommonService extends SunSpecModelService<InvCommon, InvCommonHistory, UUID, InvCommonInput> {

    public InvCommonService(InvCommonRepository repository, InvCommonHistoryRepository historyRepository, InvCommonMapper mapper, InverterRepository inverterRepository) {
        super(repository, historyRepository, mapper, inverterRepository);
    }

}
