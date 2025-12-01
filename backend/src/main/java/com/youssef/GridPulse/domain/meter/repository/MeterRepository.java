package com.youssef.GridPulse.domain.meter.repository;

import com.youssef.GridPulse.common.base.BaseRepository;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.UUID;

@GraphQlRepository(typeName = "Meter")
public interface MeterRepository extends BaseRepository<Meter, UUID> {
}
