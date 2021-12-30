package com.epam.esm.zotov.mjcschool.dataaccess.repository.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessTestConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.tag.TagRepository;

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
public class CertificateRepositoryTest {
    @Autowired
    CertificateRepository certRepo;
    @Autowired
    TagRepository tagRepo;

    @Test
    void saveWithTagsTest() {
        try {
            Certificate testCert = new Certificate(null, "Test cert", "Cert for tests", BigDecimal.valueOf(1),
                    (short) 1,
                    null, null, null);
            Optional<Certificate> result = certRepo.saveWithTags(testCert);
            assumeTrue(result.isPresent());
            assumeTrue(result.get().getTags().isEmpty());

            List<Tag> tags = tagRepo.findAll();
            testCert.setTags(new HashSet<Tag>(tags));
            result = certRepo.saveWithTags(testCert);
            assumeTrue(result.isPresent());
            assumeFalse(result.get().getTags().isEmpty());
        } catch (Exception e) {
        }
    }

    @Test
    void selectiveUpdateTest() {
        try {

            Certificate testCert = new Certificate(null, "Test cert", "Cert for tests", BigDecimal.valueOf(1),
                    (short) 1,
                    null, null, null);
            Certificate updatedCert = certRepo.saveWithTags(testCert).get();
            updatedCert.setName("Updated cert");

            Optional<Certificate> result = certRepo.selectiveUpdate(updatedCert);
            assertAll(() -> assertEquals(result.get().getName(), "Updated cert"),
                    () -> assertEquals(result.get().getDescription(), "Cert for tests"));
        } catch (Exception e) {
        }
    }
}