package com.epam.esm.zotov.mjcschool.dataaccess.repository.tag;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.CustomRepository;

import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends CustomRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    @Query(value = "SELECT TAG_ID, TAGS.NAME FROM TAGS JOIN CERTIFICATES_TAGS USING(TAG_ID) JOIN (SELECT CERTIFICATE_ID "
            + "FROM ORDERS WHERE USER_ID = (SELECT USER_ID FROM ORDERS GROUP BY USER_ID ORDER BY SUM(PURCHASE_PRICE) DESC LIMIT 1) ) "
            + "AS SPECIAL_USERS_CERTIFICATES USING(CERTIFICATE_ID) GROUP BY TAG_ID ORDER BY COUNT(TAG_ID) DESC LIMIT 1", nativeQuery = true)
    Optional<Tag> findFavoriteTagOfMostSpendingUser();
}