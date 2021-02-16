package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao {

    Optional<GiftCertificate> findById(long id);

    List<GiftCertificate> findAll(String name, String description, String[] tagNames,
                                  String sortType, String direction, Integer limit, Integer offset);

    GiftCertificate add(GiftCertificate certificate);

    GiftCertificate update(GiftCertificate certificate);

    boolean delete(long id);

    Class<GiftCertificate> getEntityClass();
}
