package com.epam.esm.zotov.mjcschool.api.controller.authorization;

import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import com.epam.esm.zotov.mjcschool.api.dto.JwtDto;
import com.epam.esm.zotov.mjcschool.api.exception.AuthorizationFailedException;
import com.epam.esm.zotov.mjcschool.api.security.jwt.JwtOperator;
import com.epam.esm.zotov.mjcschool.api.security.jwt.JwtTokenValidator;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
@Validated
public class AuthorizationControllerImpl implements AuthorizationController {
    private JwtOperator jwtUtil;
    private JwtTokenValidator tokenValidator;
    private UserService userService;

    @Autowired
    public AuthorizationControllerImpl(JwtOperator jwtUtil, UserService userService, JwtTokenValidator tokenValidator) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.tokenValidator = tokenValidator;
    }

    @Override
    public JwtDto authorize(@NotEmpty Map<String, String> credentials) {
        Optional<User> user = userService.findByUsername(credentials.get("username"));
        if (user.isEmpty() || !user.get().getPassword().equals(credentials.get("password"))) {
            throw new AuthorizationFailedException();
        } else {
            return new JwtDto(jwtUtil.createToken(user.get()), jwtUtil.createRefreshToken(user.get()));
        }
    }

    @Override
    @PostMapping("/refresh")
    public JwtDto refresh(Map<String, String> refreshToken) {
        try {
            if (tokenValidator.validateToke(refreshToken.get("refreshToken"))) {
                Optional<User> user = userService
                        .findByIdWithOrders(Long.valueOf(
                                userService.findByUsername(jwtUtil.extractSubject(refreshToken.get("refreshToken")))
                                        .get().getUserId()));
                return new JwtDto(jwtUtil.createToken(user.get()), jwtUtil.createRefreshToken(user.get()));
            } else {
                throw new AuthorizationFailedException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        throw new AuthorizationFailedException();
    }
}