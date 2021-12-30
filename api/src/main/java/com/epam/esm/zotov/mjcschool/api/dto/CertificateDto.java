package com.epam.esm.zotov.mjcschool.api.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CertificateDto extends RepresentationModel<CertificateDto> {
    @Min(value = 1)
    private Long certificateId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    private BigDecimal price;
    @Min(value = 1)
    private Short duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant lastUpdateDate;
    @NotNull
    private String[] tags;

    public Certificate convertToCertificate() {
        Set<Tag> tagSet = new HashSet<Tag>();
        for (String tag : tags) {
            tagSet.add(new Tag(null, tag));
        }
        return new Certificate(certificateId, name, description, price, duration, createDate, lastUpdateDate, tagSet);
    }

    public CertificateDto() {
        tags = new String[0];
    }

    public CertificateDto(Certificate certificate) {
        certificateId = certificate.getCertificateId();
        name = certificate.getName();
        description = certificate.getDescription();
        price = certificate.getPrice();
        duration = certificate.getDuration();
        createDate = certificate.getCreateDate();
        lastUpdateDate = certificate.getUpdateDate();
        Set<Tag> tagSet = certificate.getTags();
        if (Objects.nonNull(tagSet) && !tagSet.isEmpty()) {
            tags = tagSet.stream().map(tag -> tag.getName()).toArray(String[]::new);
        } else {
            tags = new String[0];
        }
    }
}