package com.youssef.GridPulse.domain.meter.repository;

import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import com.youssef.GridPulse.domain.meter.entity.MeterHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MeterHistoryRepository extends BaseHistoryRepository<MeterHistory, UUID> {
}
