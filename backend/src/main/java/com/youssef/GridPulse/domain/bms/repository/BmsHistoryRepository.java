package com.youssef.GridPulse.domain.bms.repository;

import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import com.youssef.GridPulse.domain.bms.entity.BmsHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BmsHistoryRepository extends BaseHistoryRepository<BmsHistory, UUID> {
}
