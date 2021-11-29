package com.epam.esm.zotov.mjcschool.dataaccess.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessTestConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.order.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
@ContextConfiguration(classes = { DataAccessConfig.class, DataAccessTestConfig.class })
public class CustomRepositoryTest {
    @Autowired
    OrderRepository repo;

    @Test
    void pagingTest() {
        List<Order> page = repo.getPage(1, 0);
        assertEquals(page.get(0).getOrderId(), 1L);

        page = repo.getPage(2, 1);
        assertEquals(page.get(0).getOrderId(), 3L);
        assertEquals(page.get(1).getOrderId(), 4L);

        page = repo.getPage(2, 2);
        assertEquals(page.get(0).getOrderId(), 5L);

        assertThrows(InvalidDataAccessApiUsageException.class, () -> repo.getPage(-11, -55));
    }
}