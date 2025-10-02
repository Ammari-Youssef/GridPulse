package com.youssef.GridPulse.domain.inverter.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.mapper.InverterMapper;
import com.youssef.GridPulse.domain.inverter.repository.InverterHistoryRepository;
import com.youssef.GridPulse.domain.inverter.repository.InverterRepository;

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
