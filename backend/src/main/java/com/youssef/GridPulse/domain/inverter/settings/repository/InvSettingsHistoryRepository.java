package com.youssef.GridPulse.domain.inverter.settings.repository;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelHistoryRepository;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettingsHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvSettingsHistoryRepository extends SunSpecModelHistoryRepository<InvSettingsHistory, UUID> {
}
