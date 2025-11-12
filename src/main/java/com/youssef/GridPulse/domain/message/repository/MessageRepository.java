package com.youssef.GridPulse.domain.message.repository;

import com.youssef.GridPulse.common.base.BaseRepository;
import com.youssef.GridPulse.domain.message.entity.Message;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends BaseRepository<Message, UUID> {
    List<Message> findByDeviceId(UUID deviceId);
}
