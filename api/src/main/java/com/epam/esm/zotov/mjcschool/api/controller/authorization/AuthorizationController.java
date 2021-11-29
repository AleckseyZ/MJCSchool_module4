package com.epam.esm.zotov.mjcschool.api.controller.authorization;

import org.springframework.web.bind.annotation.PostMapping;

public interface AuthorizationController {
    @PostMapping()
    public String authorize(String username, String password);
}