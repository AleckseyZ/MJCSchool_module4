package com.epam.esm.zotov.mjcschool.dataaccess.repository.order;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessTestConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
@ContextConfiguration(classes = { DataAccessConfig.class, DataAccessTestConfig.class })
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepo;

    @Test
    void readTest() {
        assertDoesNotThrow(() -> orderRepo.findById(1L));
        assertDoesNotThrow(() -> orderRepo.findAll());
    }

    @Test
    void deleteTest() {
        Optional<Order> order = orderRepo.findById(1L);
        assumeTrue(order.isPresent());

        orderRepo.deleteById(1L);
        order = orderRepo.findById(1L);
        assumeFalse(order.isPresent());
    }
}