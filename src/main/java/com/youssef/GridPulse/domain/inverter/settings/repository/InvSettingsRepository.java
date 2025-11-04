package com.youssef.GridPulse.domain.inverter.settings.repository;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelRepository;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvSettingsRepository extends SunSpecModelRepository<InvSettings, UUID> {
}
