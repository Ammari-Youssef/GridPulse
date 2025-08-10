package com.youssef.GridPulse.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, UUID> {
    Optional<UserHistory> findByEmail(String email);
    Optional<UserHistory> findByOriginalId(UUID originalId);
}
