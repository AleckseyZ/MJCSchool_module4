package com.epam.esm.zotov.mjcschool.api.controller.certificate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.esm.zotov.mjcschool.api.dto.CertificateDto;
import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.api.exception.RequestNotExecutedException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.service.certificate.CertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/certificates")
@Validated
public class CertificateControllerImpl implements CertificateController {
    private CertificateService certificateService;

    @Autowired
    public CertificateControllerImpl(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @Override
    @RequestMapping(params = { "pageSize", "pageNumber" })
    public ListDto<CertificateDto> getPage(int pageSize, int pageNumber) {
        List<Certificate> certificates = certificateService.getPage(pageSize, pageNumber);
        if (Objects.isNull(certificates) || certificates.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            ListDto<CertificateDto> listDto = new ListDto<CertificateDto>(
                    certificates.stream().map(cert -> new CertificateDto(cert)).collect(Collectors.toList()));

            listDto.getList().stream().forEach(tagDto -> addCommonHateoasLinks(tagDto));
            listDto.addNextPageLink(methodOn(CertificateControllerImpl.class).getPage(pageSize, ++pageNumber));
            listDto.addPreviousPageLink(methodOn(CertificateControllerImpl.class).getPage(pageSize, ++pageNumber));
            return listDto;
        }
    }

    @Override
    public CertificateDto getById(long targetId) {
        Optional<Certificate> certificate = certificateService.getById(targetId);
        if (certificate.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            CertificateDto dto = new CertificateDto(certificate.get());
            addCommonHateoasLinks(dto);
            return dto;
        }
    }

    @Override
    public CertificateDto save(CertificateDto certificate) {
        Certificate savedCertificate = certificateService.save(certificate.convertToCertificate());

        CertificateDto dto = new CertificateDto(savedCertificate);
        addCommonHateoasLinks(dto);
        return dto;
    }

    @Override
    public void delete(long targetId) {
        if (!certificateService.delete(targetId)) {
            throw new RequestNotExecutedException();
        }
    }

    @Override
    public CertificateDto selectiveUpdate(long targetId, CertificateDto updatedCertificate) {
        updatedCertificate.setCertificateId(targetId);
        Optional<Certificate> returnedCertificate = certificateService
                .selectiveUpdate(updatedCertificate.convertToCertificate());
        if (returnedCertificate.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            CertificateDto dto = new CertificateDto(returnedCertificate.get());
            addCommonHateoasLinks(dto);
            return dto;
        }
    }

    @Override
    public ListDto<CertificateDto> search(Map<String, String> searchParams) {
        int limit = Integer.parseInt(searchParams.get("limit"));
        int offset = Integer.parseInt(searchParams.get("offset"));
        List<Certificate> certificates = certificateService.search(searchParams);
        if (certificates.isEmpty() || certificates.size() < offset || limit < 0 || offset < 0) {
            throw new NoResourceFoundException();
        } else {
            if (certificates.size() > offset + limit) {
                certificates = certificates.subList(offset, limit);
            } else {
                certificates = certificates.subList(offset, certificates.size());
            }
            List<CertificateDto> dtos = new ArrayList<>();
            for (Certificate certificate : certificates) {
                CertificateDto dto = new CertificateDto(certificate);
                addCommonHateoasLinks(dto);
                dtos.add(dto);
            }
            ListDto<CertificateDto> listDto = new ListDto<CertificateDto>(dtos);
            listDto.addNextPageLink(methodOn(CertificateControllerImpl.class).getPage(limit, limit + offset));
            listDto.addPreviousPageLink(methodOn(CertificateControllerImpl.class).getPage(limit, offset - limit));
            return listDto;
        }
    }

    private void addCommonHateoasLinks(CertificateDto certificate) {
        certificate.add(linkTo(methodOn(CertificateControllerImpl.class).getById(certificate.getCertificateId()))
                .withSelfRel());
    }
}