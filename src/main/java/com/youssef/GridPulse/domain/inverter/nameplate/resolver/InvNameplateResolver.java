package com.youssef.GridPulse.domain.inverter.nameplate.resolver;

import com.youssef.GridPulse.domain.inverter.base.SunSpecModelResolver;
import com.youssef.GridPulse.domain.inverter.nameplate.dto.InvNameplateInput;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplate;
import com.youssef.GridPulse.domain.inverter.nameplate.entity.InvNameplateHistory;
import com.youssef.GridPulse.domain.inverter.nameplate.service.InvNameplateService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class InvNameplateResolver extends SunSpecModelResolver<InvNameplate, InvNameplateHistory, UUID, InvNameplateInput> {

    public InvNameplateResolver(InvNameplateService service) {
        super(service);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvNameplate> getAllInvNameplates() {
        return super.getAll();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<InvNameplate> getInvNameplatesByInverterId(@Argument UUID inverterId) {
       return super.getAllByInverterId(inverterId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvNameplateHistory> getInvNameplateHistoryByInverterId(@Argument UUID inverterId) {
       return super.getAllHistoryByInverterId(inverterId);
    }

    @QueryMapping
    public InvNameplate getInvNameplateById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvNameplate createInvNameplate(@Argument InvNameplateInput input) {
        return super.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvNameplate updateInvNameplate(@Argument UUID id, @Argument InvNameplateInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteInvNameplate(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvNameplateHistory> getInvNameplateHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvNameplateHistory> getAllInvNameplateHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvNameplateHistory getInvNameplateHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markInvNameplateHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }
}
