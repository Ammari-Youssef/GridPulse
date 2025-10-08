package com.youssef.GridPulse.domain.meter.repository;

import com.youssef.GridPulse.domain.meter.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.UUID;

@GraphQlRepository(typeName = "Meter")
public interface MeterRepository extends JpaRepository<Meter, UUID> {
}
