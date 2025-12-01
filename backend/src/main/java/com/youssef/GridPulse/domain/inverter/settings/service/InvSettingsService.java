package com.youssef.GridPulse.domain.inverter.settings.service;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelService;
import com.youssef.GridPulse.domain.inverter.inverter.repository.InverterRepository;
import com.youssef.GridPulse.domain.inverter.settings.dto.InvSettingsInput;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettingsHistory;
import com.youssef.GridPulse.domain.inverter.settings.mapper.InvSettingsMapper;
import com.youssef.GridPulse.domain.inverter.settings.repository.InvSettingsHistoryRepository;
import com.youssef.GridPulse.domain.inverter.settings.repository.InvSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvSettingsService extends SunSpecModelService<InvSettings, InvSettingsHistory, UUID, InvSettingsInput> {

    public InvSettingsService(InvSettingsRepository repository, InvSettingsHistoryRepository historyRepository, InvSettingsMapper mapper, InverterRepository inverterRepository) {
        super(repository, historyRepository, mapper, inverterRepository);
    }

}
