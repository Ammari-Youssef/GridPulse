package com.youssef.GridPulse.domain.inverter.inverter.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.inverter.mapper.InverterMapper;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterHistoryRepository;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class InverterService extends BaseService<Inverter, InverterHistory, UUID, InverterInput> {

    public InverterService(InverterRepository repository,
                           InverterHistoryRepository historyRepository,
                           InverterMapper mapper) {
        super(repository, historyRepository, mapper);
    }


    @Override
    protected UUID getId(Inverter entity) {
        return entity.getId();
    }

}
