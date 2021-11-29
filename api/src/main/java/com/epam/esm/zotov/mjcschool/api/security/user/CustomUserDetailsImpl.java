package com.epam.esm.zotov.mjcschool.api.security.user;

import java.util.ArrayList;
import java.util.Collection;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Getter
public class CustomUserDetailsImpl implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public CustomUserDetailsImpl(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}