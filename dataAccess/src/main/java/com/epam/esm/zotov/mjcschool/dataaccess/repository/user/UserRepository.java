package com.epam.esm.zotov.mjcschool.dataaccess.repository.user;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.CustomRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CustomRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.orders WHERE u.userId=:id")
    Optional<User> findByIdWithOrders(@Param("id") Long id);

    @Query("SELECT u FROM User u JOIN FETCH u.role WHERE u.username=:username")
    Optional<User> findByUsernameWithRole(@Param("username") String username);

    Optional<User> findByUsername(String username);
}