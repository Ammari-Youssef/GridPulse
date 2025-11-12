package com.youssef.GridPulse.common.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, ID extends UUID> extends JpaRepository<E, ID> {

    @Query("SELECT e FROM #{#entityName} e ORDER BY e.createdAt DESC")
    List<E> findTopNOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT e FROM #{#entityName} e WHERE e.createdAt < :after ORDER BY e.createdAt DESC")
    List<E> findTopNByCreatedAtBeforeOrderByCreatedAtDesc(@Param("after") OffsetDateTime after, Pageable pageable);

    default List<E> findTopNOrderByCreatedAtDesc(int limit) {
        return findTopNOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }

    default List<E> findTopNByCreatedAtBeforeOrderByCreatedAtDesc(OffsetDateTime after, int limit) {
        return findTopNByCreatedAtBeforeOrderByCreatedAtDesc(after, PageRequest.of(0, limit));
    }
}

