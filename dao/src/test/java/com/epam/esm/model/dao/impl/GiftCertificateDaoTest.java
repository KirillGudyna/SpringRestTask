package com.epam.esm.model.dao.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

class GiftCertificateDaoTest {
    private GiftCertificateDao giftCertificateDao;
    private EmbeddedDatabase embeddedDatabase;

    GiftCertificateDaoTest() {
    }

    @BeforeEach
    void setUp() {
        this.embeddedDatabase = (new EmbeddedDatabaseBuilder()).addDefaultScripts().setType(EmbeddedDatabaseType.H2).build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.embeddedDatabase);
        this.giftCertificateDao = new GiftCertificateDaoImpl();
        ((GiftCertificateDaoImpl)this.giftCertificateDao).setJdbcTemplate(jdbcTemplate);
        TagDaoImpl tagDao = new TagDaoImpl();
        tagDao.setJdbcTemplate(jdbcTemplate);
        ((GiftCertificateDaoImpl)this.giftCertificateDao).setGiftCertificateTagDao(tagDao);
    }

    @AfterEach
    void tearDown() {
        this.embeddedDatabase.shutdown();
    }

    @Test
    void findByIdExist() {
        Optional<GiftCertificate> optional = this.giftCertificateDao.findById(1L);
        Assertions.assertTrue(optional.isPresent() && ((GiftCertificate)optional.get()).getName().equals("РЎР°СѓРЅР° РўСЂРёС‚РѕРЅ"));
    }

    @Test
    void findByIdNotExist() {
        Optional<GiftCertificate> optional = this.giftCertificateDao.findById(199L);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void findAll() {
        List<GiftCertificate> allCertificates = this.giftCertificateDao.findAll();
        Assertions.assertEquals(10, allCertificates.size());
    }

    @Test
    void add() {
        this.giftCertificateDao.add(StaticDataProvider.ADDING_CERTIFICATE);
        List<GiftCertificate> allCertificates = this.giftCertificateDao.findAll();
        Assertions.assertEquals(11, allCertificates.size());
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = (GiftCertificate)this.giftCertificateDao.findById(1L).get();
        giftCertificate.setPrice(155);
        GiftCertificate updated = this.giftCertificateDao.update(giftCertificate);
        Assertions.assertEquals(155, updated.getPrice());
    }

    @Test
    void delete() {
        this.giftCertificateDao.delete(1L);
        Optional<GiftCertificate> optional = this.giftCertificateDao.findById(1L);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void findByTagNameExist() {
        List<GiftCertificate> certificates = this.giftCertificateDao.findByTagName("РђРєС‚РёРІРЅРѕСЃС‚СЊ");
        Assertions.assertEquals(2, certificates.size());
    }

    @Test
    void findByName() {
        List<GiftCertificate> certificates = this.giftCertificateDao.findByName("РўР°С‚Сѓ");
        Assertions.assertEquals(2, certificates.size());
    }

    @Test
    void findByDescription() {
        List<GiftCertificate> certificates = this.giftCertificateDao.findByDescription("Р‘РµСЃРїР»Р°С‚РЅР°СЏ");
        Assertions.assertEquals(2, certificates.size());
    }
}
