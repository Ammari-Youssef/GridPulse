package com.youssef.GridPulse.domain.inverter.mapper;

import com.youssef.GridPulse.common.base.BaseMapper;
import com.youssef.GridPulse.domain.base.BaseMapperTest;
import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.inverter.mapper.InverterMapper;
import com.youssef.GridPulse.domain.inverter.inverter.mapper.InverterMapperImpl;
import com.youssef.GridPulse.utils.TestSuiteUtils;


public class InverterMapperTests extends BaseMapperTest<Inverter, InverterHistory, InverterInput> {

    private final InverterMapper mapper = new InverterMapperImpl();

    @Override
    protected BaseMapper<Inverter, InverterHistory, InverterInput> getMapper() {
        return mapper;
    }

    @Override
    protected InverterInput createTestInput() {
        return TestSuiteUtils.createTestInverterInput();
    }

    @Override
    protected Inverter createTestEntity() {
        return TestSuiteUtils.createTestInverterA();
    }

    @Override
    protected InverterHistory createTestHistoryEntity() {
        return TestSuiteUtils.createTestHistoryEntityA();
    }

    @Override
    protected Inverter cloneEntity(Inverter entity) {
        return TestSuiteUtils.createTestInverter(entity);
    }

}