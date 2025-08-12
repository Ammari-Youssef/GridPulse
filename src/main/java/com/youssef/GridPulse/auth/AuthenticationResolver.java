package com.youssef.GridPulse.auth;

import com.youssef.GridPulse.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class AuthenticationResolver {

    private final AuthenticationService authenticationService;

    @MutationMapping
    public AuthenticationResponse register(@Argument("registerInput") RegisterInput registerInput) {
        return authenticationService.register(registerInput);
    }

    @MutationMapping
    public AuthenticationResponse login(@Argument("loginInput") LoginInput loginInput) {
        return authenticationService.authenticate(loginInput);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public boolean logout() {
        return authenticationService.logout();
    }

    @MutationMapping(name = "refreshToken")
    public AuthenticationResponse refreshToken(@Argument("refreshToken") String refreshToken) {
        return authenticationService.refreshToken(refreshToken);
    }

    @MutationMapping(name = "createUserWithRole")
    @PreAuthorize("hasRole('ADMIN')")
    public AuthenticationResponse createUserWithRole(
            @Argument("registerInput") RegisterInput registerInput,
            @Argument("role") String role) {
        return authenticationService.createUserWithRole(registerInput, role);
    }

    @QueryMapping(name = "getCurrentUser")
    @PreAuthorize("isAuthenticated()")
    public User getCurrentUser() {
        return authenticationService.getCurrentUser();
    }

}
