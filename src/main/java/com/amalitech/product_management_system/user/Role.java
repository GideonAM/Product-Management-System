package com.amalitech.product_management_system.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.amalitech.product_management_system.user.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(
            List.of(ADMIN_DELETE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_WRITE,
                    MANAGER_DELETE,
                    MANAGER_UPDATE,
                    MANAGER_WRITE,
                    MANAGE_READ)
    ),
    MANAGER(
            List.of(MANAGER_DELETE,
                    MANAGER_UPDATE,
                    MANAGER_WRITE,
                    MANAGE_READ)
    ),
    USER(new ArrayList<>());

    private final List<Permission> roles;

    public List<SimpleGrantedAuthority> grantedAuthorities() {
        return getRoles().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList();
    }
}
