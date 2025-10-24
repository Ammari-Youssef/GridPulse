package com.youssef.GridPulse.domain.security.repository;

import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SecurityKeyRepository extends JpaRepository<SecurityKey, UUID> {
}
