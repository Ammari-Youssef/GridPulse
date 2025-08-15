package com.youssef.GridPulse.configuration.audit;

import com.youssef.GridPulse.domain.identity.user.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * This class implements AuditorAware to provide the current auditor's information.
 * We use String as the type parameter to indicate that the auditor's identifier is either "system" or a UUID converted to a String.
 */
public class ApplicationAuditAware implements AuditorAware<String> {
    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }

        User userPrincipal = (User) auth.getPrincipal();

        return Optional.ofNullable(userPrincipal.getId().toString());
    }
}
