package com.youssef.GridPulse.domain.meter.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.meter.dto.MeterInput;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.domain.meter.entity.MeterHistory;
import com.youssef.GridPulse.domain.meter.mapper.MeterMapper;
import com.youssef.GridPulse.domain.meter.repository.MeterHistoryRepository;
import com.youssef.GridPulse.domain.meter.repository.MeterRepository;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@ConditionalOnGraphQlSchema
public class MeterService extends BaseService<Meter, MeterHistory, UUID, MeterInput> {

    public MeterService(MeterRepository repository, MeterHistoryRepository historyRepository, MeterMapper mapper) {
        super(repository, historyRepository, mapper);
    }
}
