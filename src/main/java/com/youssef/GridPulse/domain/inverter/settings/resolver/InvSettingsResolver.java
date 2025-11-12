package com.youssef.GridPulse.domain.inverter.settings.resolver;

import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageRequestInput;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageResponse;
import com.youssef.GridPulse.domain.inverter.base.SunSpecModelResolver;
import com.youssef.GridPulse.domain.inverter.settings.dto.InvSettingsInput;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettings;
import com.youssef.GridPulse.domain.inverter.settings.entity.InvSettingsHistory;
import com.youssef.GridPulse.domain.inverter.settings.service.InvSettingsService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class InvSettingsResolver extends SunSpecModelResolver<InvSettings, InvSettingsHistory, UUID, InvSettingsInput> {

    public InvSettingsResolver(InvSettingsService service) {
        super(service);
    }

    //    Pagination Offset
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<InvSettings> getAllInvSettingsPaged(@Argument PageRequestInput pageRequest) {
        return super.getAllPaged(pageRequest);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<InvSettingsHistory> getAllInvSettingsHistoryPaged(@Argument PageRequestInput pageRequest) {
        return super.getAllHistoryPaged(pageRequest);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponse<InvSettingsHistory> getInvSettingsHistoryByOriginalIdPaged(@Argument UUID originalId, @Argument PageRequestInput pageRequest) {
        return super.getHistoryByOriginalIdPaged(originalId, pageRequest);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public PageResponse<InvSettings> getInvSettingsByInverterIdPaged(@Argument UUID inverterId, @Argument PageRequestInput pageRequest) {
        return super.getAllByInverterIdPaged(inverterId, pageRequest);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public PageResponse<InvSettingsHistory> getInvSettingsHistoryByInverterIdPaged(@Argument UUID inverterId, @Argument PageRequestInput pageRequest) {
        return super.getAllHistoryByInverterIdPaged(inverterId, pageRequest);
    }

    // CRUD

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvSettings> getAllInvSettings() {
        return super.getAll();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<InvSettings> getInvSettingsByInverterId(@Argument UUID inverterId) {
       return super.getAllByInverterId(inverterId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvSettingsHistory> getInvSettingsHistoryByInverterId(@Argument UUID inverterId) {
       return super.getAllHistoryByInverterId(inverterId);
    }

    @QueryMapping
    public InvSettings getInvSettingsById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvSettings createInvSettings(@Argument InvSettingsInput input) {
        return super.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvSettings updateInvSettings(@Argument UUID id, @Argument InvSettingsInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteInvSettings(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvSettingsHistory> getInvSettingsHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InvSettingsHistory> getAllInvSettingsHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InvSettingsHistory getInvSettingsHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markInvSettingsHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }
}
