package com.youssef.GridPulse.domain.identity.user.repository;

import com.youssef.GridPulse.common.base.BaseRepository;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
