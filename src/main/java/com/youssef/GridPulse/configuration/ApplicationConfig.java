package com.youssef.GridPulse.configuration;

import com.youssef.GridPulse.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {"com.youssef.GridPulse.user", "com.youssef.GridPulse.token", })
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * AuthenticationProvider Bean configuration
     * This bean is responsible for the strategy of authenticating the user.
     * It uses the UserDetailsService to load the user details and then checks the password.
     * It is used by the authentication manager to authenticate the user.
     * We use DaoAuthProvider to authenticate the user using the UserDetailsService.
     * Other authentication providers can be used as well, such as LDAP, OAuth2, etc.
     * They fetch username differently and use different authentication methods.
     * For example, LDAP uses LDAP queries to fetch the user details.
     * OAuth2 uses OAuth2 tokens to authenticate the user.
     * Some fetches username from in-memory dbs or other sources like AWS EC2, etc.
     * The DaoAuthenticationProvider is the default authentication provider in Spring Security.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // 1. Specify the UserDetailsService we are using.
        authProvider.setPasswordEncoder(passwordEncoder()); // 2. Specify the PasswordEncoder we are using.
        return authProvider;
    }

    /**
     * PasswordEncoder Bean configuration
     * This bean is responsible for encoding the password.
     * It uses the BCryptPasswordEncoder to encode the password.
     * It is used by the authentication provider to encode the password.
     * Other password encoders can be used as well, such as Pbkdf2PasswordEncoder, SCryptPasswordEncoder, etc.
     * They use different algorithms to encode the password.
     * For example, Pbkdf2PasswordEncoder uses PBKDF2 algorithm to encode the password.
     * SCryptPasswordEncoder uses scrypt algorithm to encode the password.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // 12 is the strength of the password encoder.
    }

    /**
     * AuthenticationManager is there to coordinate the different auth providers there are.
     * it middles between spring security filters chain (auth request ,auth res) and the providers.
     * It is used by the authentication provider to authenticate the user.
     * @param config Holds information about authManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
