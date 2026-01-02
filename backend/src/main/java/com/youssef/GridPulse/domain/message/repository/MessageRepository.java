package com.youssef.GridPulse.domain.message.repository;

import com.youssef.GridPulse.common.base.BaseRepository;
import com.youssef.GridPulse.domain.message.entity.Message;
import com.youssef.GridPulse.domain.message.enums.MessageStatus;
import com.youssef.GridPulse.domain.message.enums.Severity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends BaseRepository<Message, UUID> {
    List<Message> findByDeviceId(UUID deviceId);
    Page<Message> findByDeviceId(UUID deviceId, Pageable pageable);

    Long countByStatus(MessageStatus status);
    Long countByStatusAndSeverity(MessageStatus status, Severity severity);
}
