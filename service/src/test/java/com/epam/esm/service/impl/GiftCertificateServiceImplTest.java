package com.epam.esm.service.impl;

import com.epam.esm.dataprovider.StaticDataProvider;
import com.epam.esm.model.dao.CertificateTagDao;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

class GiftCertificateServiceImplTest {
    private GiftCertificateServiceImpl service;
    private GiftCertificateDao giftCertificateDao;
    private TransactionTemplate transactionTemplate;

    GiftCertificateServiceImplTest() {
    }

    @BeforeEach
    private void setUp() {
        giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
        TagDao tagDao = Mockito.mock(TagDao.class);
        CertificateTagDao giftCertificateTagDao = Mockito.mock(CertificateTagDao.class);
        transactionTemplate = Mockito.mock(TransactionTemplate.class);
        service = new GiftCertificateServiceImpl(giftCertificateDao, tagDao, giftCertificateTagDao, transactionTemplate);
    }

    @Test
    void findById() {
        Mockito.when(giftCertificateDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(StaticDataProvider.GIFT_CERTIFICATE));
        Optional<GiftCertificate> actual = service.findById(1L);
        Optional<GiftCertificate> expected = Optional.ofNullable(StaticDataProvider.GIFT_CERTIFICATE);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        Mockito.when(giftCertificateDao.findAll()).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findAll();
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void add() {
        Mockito.when(transactionTemplate.execute(ArgumentMatchers.any())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        GiftCertificate actual = service.add(StaticDataProvider.ADDING_CERTIFICATE);
        GiftCertificate expected = StaticDataProvider.GIFT_CERTIFICATE;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void updateIfExist() {
        Mockito.when(giftCertificateDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        Mockito.when(transactionTemplate.execute(ArgumentMatchers.any())).thenReturn(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> actual = service.update(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void updateIfNotExist() {
        Mockito.when(giftCertificateDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Optional<GiftCertificate> actual = service.update(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> expected = Optional.empty();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void delete() {
        Mockito.when(transactionTemplate.execute(ArgumentMatchers.any())).thenReturn(true);
        boolean actual = service.delete(1L);
        Assertions.assertTrue(actual);
    }

    @Test
    void findByTagName() {
        Mockito.when(giftCertificateDao.findByTagName(ArgumentMatchers.anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findByTagName("OOOO", "price", "desc");
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findByName() {
        Mockito.when(giftCertificateDao.findByName(ArgumentMatchers.anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findByName("РљСѓСЂСЃС‹", null, null);
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findByDescription() {
        Mockito.when(giftCertificateDao.findByDescription(ArgumentMatchers.anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findByDescription("Р›СѓС‡С€РёРµ", null, null);
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }
}