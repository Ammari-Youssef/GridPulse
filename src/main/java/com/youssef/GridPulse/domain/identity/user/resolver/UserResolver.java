package com.youssef.GridPulse.domain.identity.user.resolver;

import com.youssef.GridPulse.common.base.BaseResolver;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageRequestInput;
import com.youssef.GridPulse.configuration.graphql.pagination.offsetBased.PageResponse;
import com.youssef.GridPulse.domain.identity.auth.dto.RegisterInput;
import com.youssef.GridPulse.domain.identity.user.dto.UpdateUserInput;
import com.youssef.GridPulse.domain.identity.user.entity.User;
import com.youssef.GridPulse.domain.identity.user.entity.UserHistory;
import com.youssef.GridPulse.domain.identity.user.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class UserResolver extends BaseResolver<User, UserHistory, UUID, RegisterInput> {

    private final UserService service;

    public UserResolver(UserService service) {
        super(service);
        this.service = service;
    }

    // Pagination Offset
    @QueryMapping
    public PageResponse<User> getAllUserPaged(@Argument PageRequestInput pageRequest) {
        return super.getAllPaged(pageRequest);
    }

    @QueryMapping
    public PageResponse<UserHistory> getAllUserHistoryPaged(@Argument PageRequestInput pageRequest) {
        return super.getAllHistoryPaged(pageRequest);
    }

    @QueryMapping
    public PageResponse<UserHistory> getUserHistoryByOriginalIdPaged(@Argument UUID originalId, @Argument PageRequestInput pageRequest) {
        return super.getHistoryByOriginalIdPaged(originalId, pageRequest);
    }

    // Common CRUD

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markUserHistorySynced(@Argument UUID id) {
        return super.markHistorySynced(id);
    }

    @QueryMapping(name = "getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return super.getAll();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@Argument UUID id) {
        return super.getById(id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserHistory> getUsersActivityHistory() {
        return super.getAllHistory();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public User updateUser(@Argument UUID id, @Argument UpdateUserInput input) {
        return service.updateUser(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUser(@Argument UUID id) {
       return super.delete(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean toggleUserEnableStatus(@Argument UUID id) {
       return service.toggleUserEnableStatus(id);
    }

}
