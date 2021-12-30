package com.epam.esm.zotov.mjcschool.service.user;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.role.RoleRepository;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    private RoleRepository roleRepository;
    private static final String DEFAULT_ROLE = "user";

    @Autowired
    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepositor) {
        this.userRepo = userRepo;
        this.roleRepository = roleRepositor;
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
        return userRepo.findByUsernameWithRole(username);
    }

    @Override
    public Optional<User> findByIdWithOrders(Long id) {
        return userRepo.findByIdWithOrders(id);
    }

    @Override
    public Optional<User> makeUser(String username, String password) {
        Optional<User> existingUser = userRepo.findByUsername(username);
        if (existingUser.isEmpty()) {
            User newUser = new User(null, username, password, roleRepository.findByName(DEFAULT_ROLE));
            return Optional.of(userRepo.save(newUser));
        } else {
            return Optional.empty();
        }
    }
}