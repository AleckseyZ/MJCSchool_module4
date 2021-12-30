package com.epam.esm.zotov.mjcschool.dataaccess.repository.certificate;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.tag.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@PropertySource("classpath:jpa.certificate.properties")
public class CustomCertificateRepositoryImpl implements CustomCertificateRepository {
    private EntityManager entityManager;
    private TagRepository tagRepo;
    @Value("${jpa.certificate.idParam}")
    private String idParam;
    @Value("${jpa.certificate.nameParam}")
    private String nameParam;
    @Value("${jpa.certificate.descriptionParam}")
    private String descriptionParam;
    @Value("${jpa.certificate.priceParam}")
    private String priceParam;
    @Value("${jpa.certificate.durationParam}")
    private String durationParam;
    @Value("${jpa.certificate.updateDate}")
    private String updateDateParam;
    private static final int EXPECTED_UPDATE_ROWS = 1;

    @Autowired
    public CustomCertificateRepositoryImpl(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Optional<Certificate> saveWithTags(Certificate certificate) {
        certificate.setTags(processTags(certificate.getTags()));

        entityManager.persist(certificate);
        if (!entityManager.contains(certificate)) {
            certificate = null;
        }

        return Optional.ofNullable(certificate);
    }

    @Override
    @Transactional
    public Optional<Certificate> selectiveUpdate(Certificate updatedCertificate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Certificate> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Certificate.class);
        Root<Certificate> root = criteriaUpdate.from(Certificate.class);

        Certificate original = entityManager.find(Certificate.class, updatedCertificate.getCertificateId());

        criteriaUpdate.set(nameParam, coalesce(updatedCertificate.getName(), original.getName()));
        criteriaUpdate.set(descriptionParam, coalesce(updatedCertificate.getDescription(), original.getDescription()));
        criteriaUpdate.set(priceParam, coalesce(updatedCertificate.getPrice(), original.getPrice()));
        criteriaUpdate.set(durationParam, coalesce(updatedCertificate.getDuration(), original.getDuration()));
        criteriaUpdate.set(updateDateParam, Instant.now());

        criteriaUpdate.where(criteriaBuilder.equal(root.get(idParam), updatedCertificate.getCertificateId()));
        int affectedRows = entityManager.createQuery(criteriaUpdate).executeUpdate();
        entityManager.flush();
        entityManager.clear();

        Certificate result = null;
        if (affectedRows == EXPECTED_UPDATE_ROWS) {
            result = entityManager.find(Certificate.class, updatedCertificate.getCertificateId());
        }

        Set<Tag> updatedTags = updatedCertificate.getTags();

        if (Objects.nonNull(updatedTags)) {
            result.setTags(processTags(updatedTags));
        }

        return Optional.ofNullable(result);
    }

    private Set<Tag> processTags(Set<Tag> tags) {
        if (Objects.nonNull(tags)) {
            if (!tags.isEmpty()) {
                Set<Tag> processedTags = new HashSet<Tag>();

                tags.stream().forEach(tag -> {
                    Optional<Tag> tempTag = tagRepo.findByName(tag.getName());
                    if (tempTag.isPresent()) {
                        processedTags.add(tempTag.get());
                    } else {
                        processedTags.add(tagRepo.save(tag));
                    }
                });
                tags = processedTags;
            }
        } else {
            tags = new HashSet<Tag>();
        }

        return tags;
    }

    private Object coalesce(Object... objects) {
        Object firstNotNull = null;

        for (Object object : objects) {
            if (Objects.nonNull(object)) {
                firstNotNull = object;
                break;
            }
        }

        return firstNotNull;
    }
}