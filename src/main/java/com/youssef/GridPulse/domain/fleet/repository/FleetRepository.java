package com.youssef.GridPulse.domain.fleet.repository;

import com.youssef.GridPulse.common.base.BaseRepository;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FleetRepository extends BaseRepository<Fleet, UUID> {
}
