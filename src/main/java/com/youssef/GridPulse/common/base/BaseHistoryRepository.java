package com.youssef.GridPulse.common.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface BaseHistoryRepository<H extends BaseHistoryEntity, ID extends UUID> extends JpaRepository<H, ID> {

    @Modifying
    @Query("UPDATE #{#entityName} h SET h.synced = true WHERE h.id = :id")
    int markAsSynced(@Param("id") ID id);

    List<H> findByOriginalId(UUID originalId);

    // Pagination methods
    Page<H> findByOriginalId(UUID originalId, Pageable pageable);

    @Query("SELECT e FROM #{#entityName} e ORDER BY e.createdAt DESC")
    List<H> findTopNOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT e FROM #{#entityName} e WHERE e.createdAt < :after ORDER BY e.createdAt DESC")
    List<H> findTopNByCreatedAtBeforeOrderByCreatedAtDesc(@Param("after") OffsetDateTime after, Pageable pageable);

    default List<H> findTopNOrderByCreatedAtDesc(int limit) {
        return findTopNOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }

    default List<H> findTopNByCreatedAtBeforeOrderByCreatedAtDesc(OffsetDateTime after, int limit) {
        return findTopNByCreatedAtBeforeOrderByCreatedAtDesc(after, PageRequest.of(0, limit));
    }
}
