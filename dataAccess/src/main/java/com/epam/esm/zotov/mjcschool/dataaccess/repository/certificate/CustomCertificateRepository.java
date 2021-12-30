package com.epam.esm.zotov.mjcschool.dataaccess.repository.certificate;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;

public interface CustomCertificateRepository {
    /**
     * Updates <code>Certificate</code> in the data source. Will only update
     * modified fields.
     * 
     * @param updatedCertificate - <code>Certificate</code> with updated fields.
     * @return <code>true</code> if update is successful.
     */
    Optional<Certificate> selectiveUpdate(Certificate updatedCertificate);

    /**
     * Saves <code>Certificate</code> in the data source while handling tags.
     * 
     * @return Optiona that contains either saved certificate or null if
     *         certificates was not saved
     */
    Optional<Certificate> saveWithTags(Certificate certificate);
}