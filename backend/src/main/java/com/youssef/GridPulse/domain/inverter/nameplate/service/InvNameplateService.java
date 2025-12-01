package com.youssef.GridPulse.domain.inverter.nameplate.service;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelService;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import com.youssef.GridPulse.domain.inverter.nameplate.dto.InvNameplateInput;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplateHistory;
import com.youssef.GridPulse.domain.inverter.nameplate.mapper.InvNameplateMapper;
import com.youssef.GridPulse.domain.inverter.nameplate.repository.InvNameplateHistoryRepository;
import com.youssef.GridPulse.domain.inverter.nameplate.repository.InvNameplateRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvNameplateService extends SunSpecModelService<InvNameplate, InvNameplateHistory, UUID, InvNameplateInput> {

    public InvNameplateService(InvNameplateRepository repository, InvNameplateHistoryRepository historyRepository, InvNameplateMapper mapper, InverterRepository inverterRepository) {
        super(repository, historyRepository, mapper, inverterRepository);
    }

}
