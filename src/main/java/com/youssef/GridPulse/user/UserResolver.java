package com.youssef.GridPulse.user;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserResolver {

    private final UserService userService;

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean markUserHistorySynced(@Argument UUID id) {
        return userService.markHistoryRecordAsSynced(id);
    }

    @QueryMapping(name = "getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<User> getUserById(@Argument UUID id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserHistory> getUsersActivityHistory() {
        return userService.getUsersActivityHistory();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public User updateUser(@Argument UUID id, @Argument UpdateUserInput input) {
        return userService.updateUser(id, input);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUser(@Argument UUID id) {
       return userService.deleteUserById(id);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean toggleUserEnableStatus(@Argument UUID id) {
       return userService.toggleUserEnableStatus(id);
    }

}
