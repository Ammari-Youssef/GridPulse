package com.youssef.GridPulse.user;

import com.youssef.GridPulse.domain.base.BaseHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserHistoryRepository extends BaseHistoryRepository<UserHistory, UUID> {
    Optional<UserHistory> findByEmail(String email);
    Optional<UserHistory> findByOriginalId(UUID originalId);

}
