package com.youssef.GridPulse.domain.inverter.nameplate.repository;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelHistoryRepository;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplateHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvNameplateHistoryRepository extends SunSpecModelHistoryRepository<InvNameplateHistory, UUID> {
}
