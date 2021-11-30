package com.epam.esm.zotov.mjcschool.api.security.user;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsServiceImpl")
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private UserService userService;

    @Autowired
    public CustomUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetailsImpl userDetails;

        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            userDetails = new CustomUserDetailsImpl(user.get());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }

        return userDetails;
    }
}