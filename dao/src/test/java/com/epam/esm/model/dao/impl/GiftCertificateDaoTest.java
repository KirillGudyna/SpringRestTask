package com.epam.esm.model.dao.impl;

import com.epam.esm.dataprovider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;

class GiftCertificateDaoTest {
    private GiftCertificateDao giftCertificateDao;
    private EmbeddedDatabase embeddedDatabase;

    GiftCertificateDaoTest() {
    }

    @BeforeEach
    void setUp() {
        embeddedDatabase = (new EmbeddedDatabaseBuilder()).addDefaultScripts().setType(EmbeddedDatabaseType.H2).build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        giftCertificateDao = new GiftCertificateDaoImpl();
        ((GiftCertificateDaoImpl)giftCertificateDao).setJdbcTemplate(jdbcTemplate);
        TagDaoImpl tagDao = new TagDaoImpl();
        tagDao.setJdbcTemplate(jdbcTemplate);
        ((GiftCertificateDaoImpl)giftCertificateDao).setGiftCertificateTagDao(tagDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void findByIdExist() {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(1L);
        Assertions.assertTrue(optional.isPresent() && (optional.get()).getName().equals("Сауна Тритон"));
    }

    @Test
    void findByIdNotExist() {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(199L);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void findAll() {
        List<GiftCertificate> allCertificates = giftCertificateDao.findAll();
        Assertions.assertEquals(10, allCertificates.size());
    }

    @Test
    void add() {
        giftCertificateDao.add(StaticDataProvider.GIFT_CERTIFICATE);
        List<GiftCertificate> allCertificates = giftCertificateDao.findAll();
        Assertions.assertEquals(11, allCertificates.size());
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = giftCertificateDao.findById(1L).get();
        giftCertificate.setPrice(155);
        GiftCertificate updated = giftCertificateDao.update(giftCertificate);
        Assertions.assertEquals(155, updated.getPrice());
    }

    @Test
    void delete() {
        giftCertificateDao.delete(1L);
        Optional<GiftCertificate> optional = giftCertificateDao.findById(1L);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void findByTagNameExist() {
        List<GiftCertificate> certificates = giftCertificateDao.findByTagName("РђРєС‚РёРІРЅРѕСЃС‚СЊ");
        Assertions.assertEquals(2, certificates.size());
    }

    @Test
    void findByName() {
        List<GiftCertificate> certificates = giftCertificateDao.findByName("РўР°С‚Сѓ");
        Assertions.assertEquals(2, certificates.size());
    }

    @Test
    void findByDescription() {
        List<GiftCertificate> certificates = giftCertificateDao.findByDescription("Р‘РµСЃРїР»Р°С‚РЅР°СЏ");
        Assertions.assertEquals(2, certificates.size());
    }
}
