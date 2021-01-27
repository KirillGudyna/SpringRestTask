package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    Optional<GiftCertificate> findById(long var1);

    List<GiftCertificate> findAll(String name,
                                  String description,
                                  String tagName,
                                  String sortType,
                                  String direction);

    GiftCertificate add(GiftCertificate var1);

    Optional<GiftCertificate> update(GiftCertificate var1);

    boolean delete(long var1);

    List<GiftCertificate> findByTagName(String var1, String var2, String var3);

    List<GiftCertificate> findByName(String var1, String var2, String var3);

    List<GiftCertificate> findByDescription(String var1, String var2, String var3);
}