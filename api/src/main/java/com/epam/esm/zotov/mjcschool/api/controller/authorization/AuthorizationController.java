package com.epam.esm.zotov.mjcschool.api.controller.authorization;

import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String authorize(@RequestParam @NotBlank String username, @RequestParam @NotBlank String password);
}