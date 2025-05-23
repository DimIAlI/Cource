package org.example.entity.account;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CUSTOMER, ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
