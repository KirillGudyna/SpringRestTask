package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.CertificateTagDao;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.transaction.support.TransactionCallback;
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
        this.giftCertificateDao = (GiftCertificateDao)Mockito.mock(GiftCertificateDao.class);
        TagDao tagDao = (TagDao)Mockito.mock(TagDao.class);
        CertificateTagDao giftCertificateTagDao = (CertificateTagDao)Mockito.mock(CertificateTagDao.class);
        this.transactionTemplate = (TransactionTemplate)Mockito.mock(TransactionTemplate.class);
        this.service = new GiftCertificateServiceImpl(this.giftCertificateDao, tagDao, giftCertificateTagDao, this.transactionTemplate);
    }

    @Test
    void findById() {
        Mockito.when(this.giftCertificateDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(StaticDataProvider.GIFT_CERTIFICATE));
        Optional<GiftCertificate> actual = this.service.findById(1L);
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        Mockito.when(this.giftCertificateDao.findAll()).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = this.service.findAll();
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void add() {
        Mockito.when(this.transactionTemplate.execute((TransactionCallback)ArgumentMatchers.any())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        GiftCertificate actual = this.service.add(StaticDataProvider.ADDING_CERTIFICATE);
        GiftCertificate expected = StaticDataProvider.GIFT_CERTIFICATE;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void updateIfExist() {
        Mockito.when(this.giftCertificateDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        Mockito.when(this.transactionTemplate.execute((TransactionCallback)ArgumentMatchers.any())).thenReturn(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> actual = this.service.update(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void updateIfNotExist() {
        Mockito.when(this.giftCertificateDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        Optional<GiftCertificate> actual = this.service.update(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> expected = Optional.empty();
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void delete() {
        Mockito.when(this.transactionTemplate.execute((TransactionCallback)ArgumentMatchers.any())).thenReturn(true);
        boolean actual = this.service.delete(1L);
        Assertions.assertTrue(actual);
    }

    @Test
    void findByTagName() {
        Mockito.when(this.giftCertificateDao.findByTagName(ArgumentMatchers.anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = this.service.findByTagName("Р’С‹С€РёРІР°РЅРёРµ", "price", "desc");
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findByName() {
        Mockito.when(this.giftCertificateDao.findByName(ArgumentMatchers.anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = this.service.findByName("РљСѓСЂСЃС‹", (String)null, (String)null);
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findByDescription() {
        Mockito.when(this.giftCertificateDao.findByDescription(ArgumentMatchers.anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = this.service.findByDescription("Р›СѓС‡С€РёРµ", (String)null, (String)null);
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        Assertions.assertEquals(actual, expected);
    }
}