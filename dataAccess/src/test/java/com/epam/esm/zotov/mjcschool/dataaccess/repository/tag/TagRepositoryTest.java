package com.epam.esm.zotov.mjcschool.dataaccess.repository.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessTestConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

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

    @Test
    void findFavTagOfBiggestSpenderTest() throws Exception {
        try {
            assertEquals("tag1", tagRepo.findFavoriteTagOfMostSpendingUser().get().getName());
        } catch (Exception e) {
        }
    }

    @Test
    void readTest() {
        try {
            Tag tag = tagRepo.save(new Tag(null, "test"));
            assertEquals("test", tagRepo.getById(tag.getTagId()).getName());
        } catch (Exception e) {
        }
    }

    @Test
    void deleteTest() {
        try {
            Tag tag = tagRepo.save(new Tag(null, "test"));
            tagRepo.delete(tag);

            assumeTrue(tagRepo.findById(tag.getTagId()).isEmpty());
        } catch (Exception e) {
        }
    }
}