package com.epam.esm.zotov.mjcschool.dataaccess.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class Audited {
    @CreationTimestamp
    @Column(name = "create_date", nullable = false, updatable = false)
    private Instant createDate;
    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private Instant updateDate;
}