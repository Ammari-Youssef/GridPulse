package com.youssef.GridPulse.domain.inverter.base;

import com.youssef.GridPulse.common.base.BaseHistoryRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface SunSpecModelHistoryRepository<SH extends SunSpecModelEntityHistory, ID extends UUID>
        extends BaseHistoryRepository<SH, ID> {

    List<SH> findByInverter_Id(ID inverterId);
}

