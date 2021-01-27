package com.epam.esm.service.impl;

import com.epam.esm.dataprovider.StaticDataProvider;
import com.epam.esm.model.dao.CertificateTagDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

class TagServiceImplTest {
    private TagService service;
    private TagDao tagDao;
    private TransactionTemplate transactionTemplate;

    TagServiceImplTest() {
    }

    @BeforeEach
    void setUp() {
        this.tagDao = Mockito.mock(TagDao.class);
        this.transactionTemplate = Mockito.mock(TransactionTemplate.class);
        CertificateTagDao giftCertificateTagDao = Mockito.mock(CertificateTagDao.class);
        this.service = new TagServiceImpl(this.tagDao, giftCertificateTagDao, this.transactionTemplate);
    }

    @Test
    void findById() {
        Mockito.when(tagDao.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(StaticDataProvider.TAG));
        Optional<Tag> actual = service.findById(1L);
        Optional<Tag> expected = Optional.ofNullable(StaticDataProvider.TAG);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        Mockito.when(tagDao.findAll()).thenReturn(StaticDataProvider.TAG_LIST);
        List<Tag> actual = service.findAll();
        List<Tag> expected = StaticDataProvider.TAG_LIST;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void add() {
        Mockito.when(tagDao.add(StaticDataProvider.ADDING_TAG)).thenReturn(StaticDataProvider.TAG);
        Tag actual = service.add(StaticDataProvider.ADDING_TAG);
        Tag expected = StaticDataProvider.TAG;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void delete() {
        Mockito.when(transactionTemplate.execute(ArgumentMatchers.any())).thenReturn(true);
        boolean actual = service.delete(1L);
        Assertions.assertTrue(actual);
    }
}
