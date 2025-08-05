package com.youssef.GridPulse.resolver;

import com.youssef.GridPulse.domain.dto.InverterInput;
import com.youssef.GridPulse.domain.entity.Inverter;
import com.youssef.GridPulse.service.InverterService;
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
    public List<Inverter> getAllInverters() {
        return inverterService.getAllInverters();
    }

    @QueryMapping
    public Inverter getInverterById(@Argument UUID id) {
        return inverterService.getInverterById(id);
    }
    // TODO: Create inverters should add along with inverters fields data the data of other entities
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Inverter createInverter(@Argument InverterInput input) {
        return inverterService.createInverter(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Inverter updateInverter(@Argument UUID id, @Argument InverterInput input) {
        return inverterService.updateInverter(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteInverter(@Argument UUID id) {
        inverterService.deleteInverter(id);
    }

}
