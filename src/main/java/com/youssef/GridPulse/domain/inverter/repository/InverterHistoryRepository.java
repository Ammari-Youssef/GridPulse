package com.youssef.GridPulse.domain.inverter.repository;

import com.youssef.GridPulse.domain.base.BaseHistoryRepository;
import com.youssef.GridPulse.domain.inverter.entity.InverterHistory;

import java.util.UUID;

public interface InverterHistoryRepository extends BaseHistoryRepository<InverterHistory, UUID> {
}
