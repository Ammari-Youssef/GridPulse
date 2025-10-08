package com.youssef.GridPulse.domain.bms.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.bms.dto.BmsInput;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.bms.entity.BmsHistory;
import com.youssef.GridPulse.domain.bms.mapper.BmsMapper;
import com.youssef.GridPulse.domain.bms.repository.BmsHistoryRepository;
import com.youssef.GridPulse.domain.bms.repository.BmsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BmsService extends BaseService<Bms, BmsHistory, UUID, BmsInput> {


    public BmsService(BmsRepository repository, BmsHistoryRepository historyRepository, BmsMapper mapper) {
        super(repository, historyRepository, mapper);
    }
}
