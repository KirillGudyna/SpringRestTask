package com.epam.esm.model.dao.impl;

import com.epam.esm.dataprovider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class GiftCertificateDaoTest {
    private GiftCertificateDao giftCertificateDao;
    private EmbeddedDatabase embeddedDatabase;

    static Stream<Arguments> findAllArgs() {
        return Stream.of(
                Arguments.of("Tattoo", null, null, null, null, 2),
                Arguments.of("Theater", null, null, null, null, 1)
        );
    }

    @BeforeEach
    void setUp() {
        embeddedDatabase = (new EmbeddedDatabaseBuilder()).addDefaultScripts().setType(EmbeddedDatabaseType.H2).build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        giftCertificateDao = new GiftCertificateDaoImpl();
        ((GiftCertificateDaoImpl) giftCertificateDao).setJdbcTemplate(jdbcTemplate);
        TagDaoImpl tagDao = new TagDaoImpl();
        tagDao.setJdbcTemplate(jdbcTemplate);
        ((GiftCertificateDaoImpl) giftCertificateDao).setGiftCertificateTagDao(tagDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void findByIdExist() {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(1L);
        Assertions.assertTrue(optional.isPresent() && (optional.get()).getName().equals("Sauna Triton"));
    }

    @Test
    void findByIdNotExist() {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(199L);
        Assertions.assertFalse(optional.isPresent());
    }

    @ParameterizedTest
    @MethodSource("findAllArgs")
    void testFindAll(String name, String description, String tagName, String sortType, String direction, int size) {
        List<GiftCertificate> actual = giftCertificateDao.findAll(name, description, tagName, sortType, direction);
        Assertions.assertEquals(size, actual.size());
    }

    @Test
    void add() {
        giftCertificateDao.add(StaticDataProvider.GIFT_CERTIFICATE);
        List<GiftCertificate> allCertificates = giftCertificateDao.findAll(null, null, null, null, null);
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
        List<GiftCertificate> certificates = giftCertificateDao.findByTagName("Activity");
        Assertions.assertEquals(2, certificates.size());
    }

    @Test
    void findByName() {
        List<GiftCertificate> certificates = giftCertificateDao.findByName("Tattoo");
        Assertions.assertEquals(2, certificates.size());
    }

    @Test
    void findByDescription() {
        List<GiftCertificate> certificates = giftCertificateDao.findByDescription("Free");
        Assertions.assertEquals(4, certificates.size());
    }
}
