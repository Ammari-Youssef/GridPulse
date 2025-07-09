package com.youssef.GridPulse.auth;

import com.youssef.GridPulse.user.User;
import com.youssef.GridPulse.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
