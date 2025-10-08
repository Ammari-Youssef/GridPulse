package com.youssef.GridPulse.domain.meter.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;
import com.youssef.GridPulse.domain.meter.dto.MeterInput;
import com.youssef.GridPulse.domain.meter.entity.Meter;
import com.youssef.GridPulse.domain.meter.entity.MeterHistory;
import com.youssef.GridPulse.domain.meter.service.MeterService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class MeterResolver extends BaseResolver<Meter, MeterHistory, UUID, MeterInput> {

    public MeterResolver(MeterService service) { super(service); }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Meter> getAllMeters() {
        return super.getAll();
    }

    @QueryMapping
    public Meter getMeterById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Meter createMeter(@Argument MeterInput input) {
        return super.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Meter updateMeter(@Argument UUID id, @Argument MeterInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteMeter(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MeterHistory> getMeterHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<MeterHistory> getAllMeterHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MeterHistory getMeterHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markMeterHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }

}
