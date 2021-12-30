package com.epam.esm.zotov.mjcschool.service.tag;

import com.epam.esm.zotov.mjcschool.dataaccess.repository.tag.TagRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @InjectMocks
    TagServiceImpl tagService;

    @Mock
    TagRepository tagRepo;

    @Test
    void findFavoriteTagOfMostSpendingUserTest() {

    }
}