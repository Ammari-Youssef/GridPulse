package com.youssef.GridPulse.domain.inverter.base;

import com.youssef.GridPulse.common.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface SunSpecModelRepository <S extends SunSpecModelEntity, ID extends UUID>
        extends BaseRepository<S, ID> {

    List<S> findByInverter_Id(ID inverterId);

    // Pagination method
    Page<S> findByInverter_Id(UUID inverterId, Pageable pageable);
}
