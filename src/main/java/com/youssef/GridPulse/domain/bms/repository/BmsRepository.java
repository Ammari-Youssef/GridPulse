package com.youssef.GridPulse.domain.bms.repository;

import com.youssef.GridPulse.domain.bms.entity.Bms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BmsRepository extends JpaRepository<Bms, UUID> {
}
