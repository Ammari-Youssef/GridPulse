package com.youssef.GridPulse.domain.bms.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;
import com.youssef.GridPulse.domain.bms.dto.BmsInput;
import com.youssef.GridPulse.domain.bms.entity.Bms;
import com.youssef.GridPulse.domain.bms.entity.BmsHistory;
import com.youssef.GridPulse.domain.bms.service.BmsService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class BmsResolver extends BaseResolver<Bms, BmsHistory, UUID, BmsInput> {

    public BmsResolver(BmsService service) { super(service); }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Bms> getAllBms() {
        return super.getAll();
    }

    @QueryMapping
    public Bms getBmsById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Bms createBms(@Argument BmsInput input) {
        return service.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Bms updateBms(@Argument UUID id, @Argument BmsInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteBms(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BmsHistory> getBmsHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BmsHistory> getAllBmsHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BmsHistory getBmsHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markBmsHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }

}
