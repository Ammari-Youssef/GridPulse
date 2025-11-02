package com.youssef.GridPulse.domain.inverter.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface SunSpecModelRepository <S extends SunSpecModelEntity, ID extends UUID>
        extends JpaRepository<S, ID> {

    List<S> findByInverter_Id(ID inverterId);
}
