package com.youssef.GridPulse.auth;

import com.youssef.GridPulse.user.User;
import com.youssef.GridPulse.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthenticationResolver {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @MutationMapping
    public AuthenticationResponse register(@Argument("registerInput") RegisterInput registerInput) {
        return authenticationService.register(registerInput);
    }

    @MutationMapping
    public AuthenticationResponse login(@Argument("loginInput") LoginInput loginInput) {
        return authenticationService.authenticate(loginInput);
    }

    @QueryMapping(name = "getAllUsers")
    public List<User> getAllUsers(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new AccessDeniedException("Forbidden");
        }
        return userRepository.findAll();
    }

    @MutationMapping(name = "createUserWithRole")
    public AuthenticationResponse createUserWithRole(
            @Argument("registerInput") RegisterInput registerInput,
            @Argument("role") String role) {
        return authenticationService.createUserWithRole(registerInput, role);
    }

    @QueryMapping(name = "getCurrentUser")
    public User getCurrentUser() {
        return authenticationService.getCurrentUser();
    }
}
