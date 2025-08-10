package com.youssef.GridPulse.repository;

import com.youssef.GridPulse.domain.entity.Inverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InverterRepository extends JpaRepository<Inverter, UUID> {
}
