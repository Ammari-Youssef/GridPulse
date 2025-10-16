package com.youssef.GridPulse.domain.fleet.service;

import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.fleet.entity.FleetHistory;
import com.youssef.GridPulse.domain.fleet.dto.FleetInput;
import com.youssef.GridPulse.domain.fleet.mapper.FleetMapper;
import com.youssef.GridPulse.domain.fleet.repository.FleetHistoryRepository;
import com.youssef.GridPulse.domain.fleet.repository.FleetRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FleetService extends BaseService<Fleet, FleetHistory, UUID, FleetInput> {
    public FleetService(FleetRepository repo, FleetHistoryRepository historyRepo, FleetMapper mapper) {
        super(repo, historyRepo, mapper);
    }
}
