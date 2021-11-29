package com.epam.esm.zotov.mjcschool.api.controller.authorization;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.api.exception.AuthorizationFailedException;
import com.epam.esm.zotov.mjcschool.api.security.jwt.JwtUtil;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
public class AuthorizationControllerImpl implements AuthorizationController {
    private JwtUtil jwtUtil;
    private UserService userService;

    @Autowired
    public AuthorizationControllerImpl(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public String authorize(String username, String password) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new AuthorizationFailedException();
        } else {
            return jwtUtil.createToken(user.get());
        }
    }
}