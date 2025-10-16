package com.youssef.GridPulse.domain.fleet.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;

import com.youssef.GridPulse.domain.fleet.dto.FleetInput;
import com.youssef.GridPulse.domain.fleet.entity.Fleet;
import com.youssef.GridPulse.domain.fleet.entity.FleetHistory;
import com.youssef.GridPulse.domain.fleet.service.FleetService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class FleetResolver extends BaseResolver<Fleet, FleetHistory, UUID, FleetInput> {

    public FleetResolver(FleetService service) {
        super(service);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Fleet> getAllFleets() {
        return super.getAll();
    }

    @QueryMapping
    public Fleet getFleetById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Fleet createFleet(@Argument FleetInput input) {
        return super.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Fleet updateFleet(@Argument UUID id, @Argument FleetInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteFleet(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<FleetHistory> getFleetHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<FleetHistory> getAllFleetHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FleetHistory getFleetHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markFleetHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }


}
