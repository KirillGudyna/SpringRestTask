package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {

    Optional<GiftCertificateDto> findById(long id);

    List<GiftCertificateDto> findAll(String name,
                                  String description,
                                  String tagNames,
                                  String sortType,
                                  String direction,
                                  Integer limit,
                                  Integer offset);

    GiftCertificateDto add(GiftCertificateDto certificateDto);

    Optional<GiftCertificateDto> update(GiftCertificateDto certificate);

    boolean delete(long id);

}