package com.youssef.GridPulse.domain.inverter.inverter.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;
import com.youssef.GridPulse.common.base.BaseService;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageRequestInput;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageResponse;

import com.youssef.GridPulse.domain.inverter.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.inverter.entity.InverterHistory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class InverterResolver extends BaseResolver<Inverter, InverterHistory, UUID, InverterInput> {

    public InverterResolver(BaseService<Inverter, InverterHistory, UUID, InverterInput> service) {
        super(service);
    }

    // Pagination Offset
    @QueryMapping
    public PageResponse<Inverter> getAllInverterPaged(@Argument PageRequestInput pageRequest) {
        return super.getAllPaged(pageRequest);
    }

    @QueryMapping
    public PageResponse<InverterHistory> getAllInverterHistoryPaged(@Argument PageRequestInput pageRequest) {
        return super.getAllHistoryPaged(pageRequest);
    }

    @QueryMapping
    public PageResponse<InverterHistory> getInverterHistoryByOriginalIdPaged(@Argument UUID originalId, @Argument PageRequestInput pageRequest) {
        return super.getHistoryByOriginalIdPaged(originalId, pageRequest);
    }

    // Common CRUD

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Inverter> getAllInverters() {
        return super.getAll();
    }

    @QueryMapping
    public Inverter getInverterById(@Argument UUID id) {
        return super.getById(id);
    }

    // TODO: Create inverters should add along with inverters fields data the data of other entities
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Inverter createInverter(@Argument InverterInput input) {
        return super.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Inverter updateInverter(@Argument UUID id, @Argument InverterInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteInverter(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InverterHistory> getInverterHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InverterHistory> getAllInverterHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InverterHistory getInverterHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markInverterHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }
}
