package com.epam.esm.zotov.module2.service.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Role;
import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.role.RoleRepository;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.user.UserRepository;
import com.epam.esm.zotov.mjcschool.service.user.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepo;
    @Mock
    RoleRepository roleRepo;

    @Test
    void makeUserTest() {
        when(userRepo.findByUsername("present")).thenReturn(Optional.of(new User()));
        assertTrue(userService.makeUser("present", null).isEmpty());

        Role testRole = new Role();
        when(roleRepo.findByName("user")).thenReturn(testRole);

        User testUser = new User(null, "absent", "password", testRole);

        when(userRepo.findByUsername("absent")).thenReturn(Optional.empty());
        when(userRepo.save(testUser)).thenReturn(testUser);

        assertAll(() -> assertTrue(userService.makeUser("absent", "password").isPresent()),
                () -> assertEquals(userService.makeUser("absent", "password").get().getUsername(), "absent"),
                () -> assertEquals(userService.makeUser("absent", "password").get().getPassword(), "password"));
    }
}