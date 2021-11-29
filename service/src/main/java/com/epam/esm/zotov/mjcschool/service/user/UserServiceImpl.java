package com.epam.esm.zotov.mjcschool.service.user;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public List<User> getPage(int pageSize, int pageNumber) {
        return userRepo.getPage(pageSize, pageNumber);
    }

    @Override
    public Optional<User> getById(long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}