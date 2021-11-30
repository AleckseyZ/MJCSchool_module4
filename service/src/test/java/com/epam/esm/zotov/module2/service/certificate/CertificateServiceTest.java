package com.epam.esm.zotov.module2.service.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.certificate.CertificateRepository;
import com.epam.esm.zotov.mjcschool.service.certificate.CertificateServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceTest {
    @InjectMocks
    CertificateServiceImpl certService;
    @Mock
    CertificateRepository certRepo;
    List<Certificate> certificates;

    Tag tag1;
    Tag tag2;
    Tag tag3;

    Certificate cert1;
    Certificate cert2;
    Certificate cert3;

    @BeforeEach
    void initCertificates() {
        List<Certificate> certificates = new ArrayList<>();

        Tag tag1 = new Tag(null, "tag1");
        Tag tag2 = new Tag(null, "tag2");
        Tag tag3 = new Tag(null, "tag3");

        Set<Tag> tags1 = new HashSet<>();
        tags1.add(tag1);
        Set<Tag> tags2 = new HashSet<>();
        tags2.add(tag1);
        tags2.add(tag2);
        Set<Tag> tags3 = new HashSet<>();
        tags3.add(tag2);
        tags3.add(tag3);

        Certificate cert1 = new Certificate(null, "cert1", "desc1", BigDecimal.valueOf(1), (short) 1, null, null,
                tags1);
        Certificate cert2 = new Certificate(null, "cert2", "desc2", BigDecimal.valueOf(2), (short) 2, null, null,
                tags2);
        Certificate cert3 = new Certificate(null, "cert3", "desc3", BigDecimal.valueOf(3), (short) 3, null, null,
                tags3);

        certificates.add(cert1);
        certificates.add(cert2);
        certificates.add(cert3);

        when(certRepo.findAll()).thenReturn(certificates);
    }

    @Test
    void searchByTagsTest() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("tagName", "tag1,tag2");
        assertEquals("cert2", certService.search(searchParams).get(0).getName());

        searchParams = new HashMap<>();
        searchParams.put("tagName", "tag2,tag3");
        assertEquals("cert3", certService.search(searchParams).get(0).getName());
    }

    @Test
    void searchByNameTest() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("name", "cert1");
        assertEquals("cert1", certService.search(searchParams).get(0).getName());

        searchParams = new HashMap<>();
        searchParams.put("name", "cert2");
        assertEquals("cert2", certService.search(searchParams).get(0).getName());
    }

    @Test
    void searchByDescTest() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("description", "desc1");
        assertEquals("cert1", certService.search(searchParams).get(0).getName());

        searchParams = new HashMap<>();
        searchParams.put("description", "desc3");
        assertEquals("cert3", certService.search(searchParams).get(0).getName());
    }

    @Test
    void sortByNameTest() {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("sortByName", "ASC");
        assertEquals("cert3", certService.search(searchParams).get(0).getName());

        searchParams = new HashMap<>();
        searchParams.put("sortByName", "DESC");
        assertEquals("cert1", certService.search(searchParams).get(0).getName());
    }
}