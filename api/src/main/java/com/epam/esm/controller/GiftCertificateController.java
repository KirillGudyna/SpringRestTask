package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.ErrorCode;
import com.epam.esm.util.HateoasData;
import com.epam.esm.validation.GiftCertificateValidator;
import com.epam.esm.validation.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Gift certificate controller
 */
@RestController
@RequestMapping(value = "/certificates")
public class GiftCertificateController {
    private GiftCertificateService service;
    private ExceptionProvider exceptionProvider;

    @Autowired
    public void setService(GiftCertificateService service) {
        this.service = service;
    }

    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    /**
     * End point for findAllGiftCertificates request.
     * Optionally could be used with different parameters for filtering and sorting.
     */
    @GetMapping
    @PermitAll
    public List<GiftCertificateDto> findAll(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "description", required = false) String description,
                                            @RequestParam(value = "tag", required = false) String tagName,
                                            @RequestParam(value = "sort", required = false) String sortType,
                                            @RequestParam(value = "direction", required = false) String direction,
                                            @RequestParam(required = false) Integer limit,
                                            @RequestParam(required = false) Integer offset) {
        if (!GiftEntityValidator.correctOptionalParameters(name, description, tagName, sortType, direction)) {
            throw exceptionProvider.wrongParameterFormatException(ErrorCode.WRONG_OPTIONAL_PARAMETERS);
        }
        return service.findAll(name, description, tagName, sortType, direction, limit, offset)
                .stream()
                .map(GiftCertificateController::addSelfLink)
                .collect(Collectors.toList());
    }

    /**
     * End point for finding gift certificate by id request.
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    @PermitAll
    public GiftCertificateDto findById(@PathVariable long id) {
        GiftCertificateDto giftCertificate = service.findById(id)
                .orElseThrow(
                        () -> exceptionProvider.giftEntityNotFoundException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
                );
        return addSelfLink(giftCertificate);
    }

    /**
     * End point for adding new gift certificate request.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('certificate:write')")
    public GiftCertificateDto create(@RequestBody GiftCertificateDto certificate) {
        if (!GiftCertificateValidator.isGiftCertificateDataCorrect(certificate)) {
            throw exceptionProvider.wrongParameterFormatException(ErrorCode.CERTIFICATE_WRONG_PARAMETERS);
        }
        GiftCertificateDto createdCertificate = service.add(certificate);
        return addSelfLink(createdCertificate);
    }

    /**
     * End point for updating gift certificate request.
     */
    @PatchMapping("/{id:^[1-9]\\d{0,18}$}")
    @PreAuthorize("hasAuthority('certificate:update')")
    public GiftCertificateDto update(@RequestBody GiftCertificateDto certificate, @PathVariable long id) {
        if (!GiftCertificateValidator.isGiftCertificateOptionalDataCorrect(certificate)) {
            throw exceptionProvider.wrongParameterFormatException(ErrorCode.CERTIFICATE_WRONG_PARAMETERS);
        }
        certificate.setId(id);
        GiftCertificateDto updatedCertificate = service.update(certificate)
                .orElseThrow(() -> exceptionProvider.giftEntityNotFoundException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND));
        return addSelfLink(updatedCertificate);
    }

    /**
     * End point for deleting gift certificate request.
     */
    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    @PreAuthorize("hasAuthority('certificate:delete')")
    public boolean delete(@PathVariable long id) {
        return service.delete(id);
    }

    /**
     * Method adds HATEOAS link to GiftCertificateDTO entity
     *
     * @param certificate the certificate
     * @return the gift certificate dto
     */
    public static GiftCertificateDto addSelfLink(GiftCertificateDto certificate) {
        if (certificate.getTags() != null) {
            certificate.setTags(
                    certificate.getTags().stream().map(TagController::addLinks).collect(Collectors.toList())
            );
        }
        return certificate
                .add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel())
                .add(linkTo(GiftCertificateController.class)
                        .withRel(HateoasData.POST)
                        .withName(HateoasData.ADD_CERTIFICATE))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .findById(certificate.getId()))
                        .withRel(HateoasData.PATCH)
                        .withName(HateoasData.UPDATE_CERTIFICATE_FIELDS))
                .add(linkTo(methodOn(GiftCertificateController.class)
                        .findById(certificate.getId()))
                        .withRel(HateoasData.DELETE)
                        .withName(HateoasData.DELETE_CERTIFICATE));
    }
}
