package com.epam.esm.zotov.mjcschool.dataaccess.repository.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.sql.DataSource;

import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessTestConfig;

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
public class TagRepositoryTest {
    @Autowired
    TagRepository tagRepo;
    @Autowired
    DataSource dataSource;

    @Test
    void findFavTagOfBiggestSpenderTest() throws Exception {
        assertEquals("tag1", tagRepo.findFavoriteTagOfMostSpendingUser().get().getName());
    }
}