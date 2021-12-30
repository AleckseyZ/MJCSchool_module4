package com.epam.esm.zotov.mjcschool.dataaccess.repository.certificate;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.CustomRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificateRepository extends CustomRepository<Certificate, Long>, CustomCertificateRepository {
    @Query("SELECT c FROM Certificate c LEFT JOIN FETCH c.tags WHERE c.certificateId=:id")
    Optional<Certificate> findByIdWithTags(@Param("id") Long id);

    @Query("SELECT c FROM Certificate c LEFT JOIN FETCH c.tags")
    List<Certificate> findAllWithTags();
}