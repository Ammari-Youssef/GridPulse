package com.youssef.GridPulse.domain.inverter.repository;

import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InverterRepository extends JpaRepository<Inverter, UUID> {
}
