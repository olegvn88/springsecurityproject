package com.authspring.SpringSecurityDemo.model;

import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developers:write");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
