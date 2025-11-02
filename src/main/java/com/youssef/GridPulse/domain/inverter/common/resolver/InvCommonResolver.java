package com.youssef.GridPulse.domain.inverter.common.resolver;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelResolver;
import com.youssef.GridPulse.domain.inverter.common.dto.InvCommonInput;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommon;
import com.youssef.GridPulse.domain.inverter.common.entity.InvCommonHistory;
import com.youssef.GridPulse.domain.inverter.common.service.InvCommonService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class InvCommonResolver extends SunSpecModelResolver<InvCommon, InvCommonHistory, UUID, InvCommonInput> {

    public InvCommonResolver(InvCommonService service) {
        super(service);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvCommon> getAllInvCommons() {
        return super.getAll();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<InvCommon> getInvCommonsByInverterId(@Argument UUID inverterId) {
       return super.getAllByInverterId(inverterId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvCommonHistory> getInvCommonHistoryByInverterId(@Argument UUID inverterId) {
       return super.getAllHistoryByInverterId(inverterId);
    }

    @QueryMapping
    public InvCommon getInvCommonById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvCommon createInvCommon(@Argument InvCommonInput input) {
        return super.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvCommon updateInvCommon(@Argument UUID id, @Argument InvCommonInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteInvCommon(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvCommonHistory> getInvCommonHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvCommonHistory> getAllInvCommonHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvCommonHistory getInvCommonHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markInvCommonHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }
}
