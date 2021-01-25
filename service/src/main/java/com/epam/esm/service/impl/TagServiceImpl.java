package com.epam.esm.service.impl;

import com.epam.esm.model.dao.CertificateTagDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private TagDao dao;
    private CertificateTagDao giftCertificateTagDao;
    private TransactionTemplate transactionTemplate;

    public TagServiceImpl() {
    }

    public TagServiceImpl(TagDao dao, CertificateTagDao giftCertificateTagDao, TransactionTemplate transactionTemplate) {
        this.dao = dao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.transactionTemplate = transactionTemplate;
    }

    @Autowired
    public void setDao(TagDao dao) {
        this.dao = dao;
    }

    @Autowired
    public void setGiftCertificateTagDao(CertificateTagDao giftCertificateTagDao) {
        this.giftCertificateTagDao = giftCertificateTagDao;
    }

    @Autowired
    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    public Optional<Tag> findById(long id) {
        return dao.findById(id);
    }

    public List<Tag> findAll() {
        return dao.findAll();
    }

    public Tag add(Tag entity) {
        return dao.add(entity);
    }

    public boolean delete(long id) {
        return this.transactionTemplate.execute(transactionStatus -> {
            giftCertificateTagDao.deleteByTagId(id);
            return dao.delete(id);
        });
    }
}