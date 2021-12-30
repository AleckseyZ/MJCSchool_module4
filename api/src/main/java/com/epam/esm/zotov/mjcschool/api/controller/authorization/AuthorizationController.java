package com.epam.esm.zotov.mjcschool.api.controller.authorization;

import java.util.Map;

import javax.validation.constraints.NotEmpty;

import com.epam.esm.zotov.mjcschool.api.dto.JwtDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Defines restful methods related to authorization
 */
public interface AuthorizationController {
    /**
     * Checks if there is a user with fitting credentials. If there is returns
     * authorization token
     * 
     * @return authorization token, for example, JWT
     */
    @PostMapping()
    public JwtDto authorize(@RequestBody @NotEmpty Map<String, String> credentials);

    public JwtDto refresh(@RequestBody @NotEmpty Map<String, String> refreshToken);
}