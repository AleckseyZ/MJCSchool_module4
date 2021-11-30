package com.epam.esm.zotov.mjcschool.api.controller.authorization;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.epam.esm.zotov.mjcschool.api.exception.AuthorizationFailedException;
import com.epam.esm.zotov.mjcschool.api.security.jwt.JwtOperator;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
@Validated
public class AuthorizationControllerImpl implements AuthorizationController {
    private JwtOperator jwtUtil;
    private UserService userService;

    @Autowired
    public AuthorizationControllerImpl(JwtOperator jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public String authorize(@NotBlank String username, @NotBlank String password) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new AuthorizationFailedException();
        } else {
            return jwtUtil.createToken(user.get());
        }
    }
}