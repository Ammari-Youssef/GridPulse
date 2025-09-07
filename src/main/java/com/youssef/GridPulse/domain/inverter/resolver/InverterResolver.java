package com.youssef.GridPulse.domain.inverter.resolver;

import com.youssef.GridPulse.domain.inverter.dto.InverterInput;
import com.youssef.GridPulse.domain.inverter.entity.Inverter;
import com.youssef.GridPulse.domain.inverter.entity.InverterHistory;
import com.youssef.GridPulse.domain.inverter.service.InverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@PreAuthorize("isAuthenticated()")
public class InverterResolver {

    private final InverterService inverterService;


    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Inverter> getAllInverters() {
        return inverterService.getAll();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated() ")
    public Inverter getInverterById(@Argument UUID id) {
        return inverterService.getEntityById(id);
    }

    // TODO: Create inverters should add along with inverters fields data the data of other entities
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Inverter createInverter(@Argument InverterInput input) {
        return inverterService.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Inverter updateInverter(@Argument UUID id, @Argument InverterInput input) {
        return inverterService.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteInverter(@Argument UUID id) {
        return inverterService.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InverterHistory> getInverterHistory(@Argument UUID originalId) {
        return inverterService.findHistoryByOriginalId(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<InverterHistory> getAllInverterHistory() {
        return inverterService.findAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InverterHistory getInverterHistoryById(@Argument UUID historyId) {
        return inverterService.findHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markInverterHistorySynced(@Argument UUID id) {
        return inverterService.markHistoryRecordAsSynced(id);
    }

}
