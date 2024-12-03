package com.amalitech.product_management_system.config;

import com.amalitech.product_management_system.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication connectedUser = SecurityContextHolder.getContext().getAuthentication();
        if (connectedUser == null
                || connectedUser instanceof AnonymousAuthenticationToken
                || !connectedUser.isAuthenticated()) {
            return Optional.empty();
        }

        User user = (User) connectedUser.getPrincipal();
        return Optional.ofNullable(user.getId());
    }
}
