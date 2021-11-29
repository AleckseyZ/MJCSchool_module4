package com.epam.esm.zotov.mjcschool.service.user;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.service.ReadService;

public interface UserService extends ReadService<User> {
    public Optional<User> findByUsername(String username);
}