package com.youssef.GridPulse.domain.fleet.repository;

import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import com.youssef.GridPulse.domain.fleet.entity.FleetHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FleetHistoryRepository extends BaseHistoryRepository<FleetHistory, UUID> {
}
