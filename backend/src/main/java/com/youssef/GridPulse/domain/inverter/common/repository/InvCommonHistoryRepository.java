package com.youssef.GridPulse.domain.inverter.common.repository;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelHistoryRepository;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommonHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvCommonHistoryRepository extends SunSpecModelHistoryRepository<InvCommonHistory, UUID> {
}
