package com.youssef.GridPulse.domain.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

@NoRepositoryBean
public interface BaseHistoryRepository<T extends BaseHistoryEntity, ID extends UUID> extends JpaRepository<T, ID> {

    @Modifying
    @Query("UPDATE #{#entityName} h SET h.synced = true WHERE h.id = :id")
    int markAsSynced(@Param("id") ID id);
}
