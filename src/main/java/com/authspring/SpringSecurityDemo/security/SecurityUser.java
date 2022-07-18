package com.authspring.SpringSecurityDemo.security;

import com.authspring.SpringSecurityDemo.model.Status;
import com.authspring.SpringSecurityDemo.model.User;
import java.util.Collection;
import java.util.List;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class SecurityUser implements UserDetails {

    private String username;
    private String password;
    private final List<GrantedAuthority> authorities;
    private final Boolean isActive;

    public SecurityUser(String username, String password, List<GrantedAuthority> authorities, Boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(),
            user.getStatus().equals(Status.ACTIVE),
            user.getStatus().equals(Status.ACTIVE),
            user.getStatus().equals(Status.ACTIVE),
            user.getStatus().equals(Status.ACTIVE),
            user.getRole().getAuthorities()
        );
    }
}
