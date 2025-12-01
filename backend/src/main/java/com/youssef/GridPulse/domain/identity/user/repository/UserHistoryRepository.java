package com.youssef.GridPulse.domain.identity.user.repository;

import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserHistoryRepository extends BaseHistoryRepository<UserHistory, UUID> {
}
