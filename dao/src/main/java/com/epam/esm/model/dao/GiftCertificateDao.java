package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    Optional<GiftCertificate> findById(long var1);

    List<GiftCertificate> findAll(String name,
                                  String description,
                                  String tagName,
                                  String sortType,
                                  String direction);

    GiftCertificate add(GiftCertificate var1);

    GiftCertificate update(GiftCertificate var1);

    boolean delete(long var1);

    List<GiftCertificate> findByTagName(String var1);

    List<GiftCertificate> findByName(String var1);

    List<GiftCertificate> findByDescription(String var1);
}
