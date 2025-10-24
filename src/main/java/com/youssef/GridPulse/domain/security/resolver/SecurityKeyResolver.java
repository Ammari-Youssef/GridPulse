package com.youssef.GridPulse.domain.security.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;
import com.youssef.GridPulse.domain.security.dto.ImportSecurityKeyInput;
import com.youssef.GridPulse.domain.security.entity.SecurityKey;
import com.youssef.GridPulse.domain.security.entity.SecurityKeyHistory;
import com.youssef.GridPulse.domain.security.service.SecurityKeyService;
import com.youssef.GridPulse.domain.security.dto.SecurityKeyInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class SecurityKeyResolver extends BaseResolver<SecurityKey, SecurityKeyHistory, UUID, SecurityKeyInput> {

    private final SecurityKeyService service;

    public SecurityKeyResolver(SecurityKeyService service) {
        super(service);
        this.service = service;
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SecurityKey> getAllSecurityKeys() {
        return super.getAll();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SecurityKey getSecurityKeyById(@Argument UUID id) {
        return super.getById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SecurityKey createSecurityKey(@Argument SecurityKeyInput input) {
        return service.create(input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SecurityKey importSecurityKey(@Argument ImportSecurityKeyInput input) {
        return service.importKey(input);
    }


    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SecurityKey updateSecurityKey(@Argument UUID id, @Argument SecurityKeyInput input) {
        return super.update(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteSecurityKey(@Argument UUID id) {
        return super.delete(id);
    }

    // History methods
    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SecurityKeyHistory> getSecurityKeyHistory(@Argument UUID originalId) {
        return super.getHistory(originalId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SecurityKeyHistory> getAllSecurityKeyHistory() {
        return super.getAllHistory();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SecurityKeyHistory getSecurityKeyHistoryById(@Argument UUID historyId) {
        return super.getHistoryById(historyId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markSecurityKeyHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }
}
