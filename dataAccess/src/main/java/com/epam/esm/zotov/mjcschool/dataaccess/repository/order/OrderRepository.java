package com.epam.esm.zotov.mjcschool.dataaccess.repository.order;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.CustomRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CustomRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.user JOIN FETCH o.certificate WHERE o.orderId=:id")
    Optional<Order> findByIdWithUserAndCertificate(@Param("id") Long id);

    @Query("SELECT o FROM Order o JOIN FETCH o.user JOIN FETCH o.certificate")
    List<Order> findAllWithUserAndCertificate();
}