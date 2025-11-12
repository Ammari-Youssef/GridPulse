package com.youssef.GridPulse.domain.inverter.nameplate.repository;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelRepository;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvNameplateRepository extends SunSpecModelRepository<InvNameplate, UUID> {
}
