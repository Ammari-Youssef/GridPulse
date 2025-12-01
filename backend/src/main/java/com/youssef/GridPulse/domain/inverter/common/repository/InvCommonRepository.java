package com.youssef.GridPulse.domain.inverter.common.repository;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelRepository;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvCommonRepository extends SunSpecModelRepository<InvCommon, UUID> {
}
