package com.youssef.GridPulse.domain.message.repository;

import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import com.youssef.GridPulse.domain.message.entity.MessageHistory;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageHistoryRepository extends BaseHistoryRepository<MessageHistory, UUID> {
}
