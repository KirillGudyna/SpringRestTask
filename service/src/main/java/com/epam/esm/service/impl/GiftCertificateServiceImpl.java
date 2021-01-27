package com.epam.esm.service.impl;

import com.epam.esm.comparator.CertificateTagComparator;
import com.epam.esm.model.dao.CertificateTagDao;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.CertificateTagDaoImpl;
import com.epam.esm.model.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String DASH = "-";
    private static final String UNDER_SCOPE = "_";

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private CertificateTagDao giftCertificateTagDao;
    private TransactionTemplate transactionTemplate;

    public GiftCertificateServiceImpl() {
    }

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao, CertificateTagDao giftCertificateTagDao, TransactionTemplate transactionTemplate) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.transactionTemplate = transactionTemplate;
    }

    @Autowired
    public void setGiftCertificateDao(GiftCertificateDaoImpl giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Autowired
    public void setGiftCertificateTagDao(CertificateTagDaoImpl giftCertificateTagDao) {
        this.giftCertificateTagDao = giftCertificateTagDao;
    }

    @Autowired
    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    public Optional<GiftCertificate> findById(long id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public List<GiftCertificate> findAll(String name,
                                         String description,
                                         String tagName,
                                         String sortType,
                                         String direction) {
        if (sortType != null) {
            sortType = sortType.replace(DASH, UNDER_SCOPE);
        }
        return giftCertificateDao.findAll(name, description, tagName, sortType, direction);
    }

    public GiftCertificate add(GiftCertificate certificate) {
        String currentDate = getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        return transactionTemplate.execute(transactionStatus -> {
            GiftCertificate addedCertificate = giftCertificateDao.add(certificate);
            addTags(addedCertificate, certificate.getTags());
            return addedCertificate;
        });
    }

    private void addTags(GiftCertificate added, List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {

            for (Tag tag : tags) {
                Tag addedTag = addTag(added.getId(), tag);
                added.addTag(addedTag);
            }
        }

    }

    private Tag addTag(long certificateId, Tag tag) {
        Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
        Tag addedTag = optionalTag.orElseGet(() -> tagDao.add(tag));
        giftCertificateTagDao.add(certificateId, addedTag.getId());
        return addedTag;
    }

    public Optional<GiftCertificate> update(GiftCertificate certificate) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate foundCertificate = optional.get();
            updateNotEmptyFields(certificate, foundCertificate);
            certificate.setLastUpdateDate(getCurrentDateIso());
            GiftCertificate updatedCertificate = transactionTemplate.execute(transactionStatus -> {
                GiftCertificate updatingCertificate = giftCertificateDao.update(foundCertificate);
                if (certificate.getTags() != null) {
                    giftCertificateTagDao.deleteAllTags(updatingCertificate.getId());
                    updatingCertificate.clearAllTags();
                    addTags(updatingCertificate, certificate.getTags());
                }
                return updatingCertificate;
            });
            optional = Optional.ofNullable(updatedCertificate);
        }

        return optional;
    }

    private void updateNotEmptyFields(GiftCertificate source, GiftCertificate foundCertificate) {
        if (source.getName() != null) {
            foundCertificate.setName(source.getName());
        }

        if (source.getDescription() != null) {
            foundCertificate.setDescription(source.getDescription());
        }

        if (source.getPrice() != null) {
            foundCertificate.setPrice(source.getPrice());
        }

        if (source.getDuration() != null) {
            foundCertificate.setDuration(source.getDuration());
        }

        if (source.getTags() != null) {
            foundCertificate.setTags(source.getTags());
        }

    }

    public boolean delete(long id) {
        return transactionTemplate.execute(transactionStatus -> {
            giftCertificateTagDao.deleteAllTags(id);
            return giftCertificateDao.delete(id);
        });
    }

    private String getCurrentDateIso() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    public List<GiftCertificate> findByTagName(String tagName, String sortType, String direction) {
        List<GiftCertificate> certificates = giftCertificateDao.findByTagName(tagName);
        sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    public List<GiftCertificate> findByName(String name, String sortType, String direction) {
        List<GiftCertificate> certificates = giftCertificateDao.findByName(name);
        sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    public List<GiftCertificate> findByDescription(String description, String sortType, String direction) {
        List<GiftCertificate> certificates = giftCertificateDao.findByDescription(description);
        sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    private void sortIfNecessary(List<GiftCertificate> certificates, String sortType, String direction) {
        Optional<Comparator<GiftCertificate>> optional = CertificateTagComparator.provide(sortType, direction);
        Objects.requireNonNull(certificates);
        optional.ifPresent(certificates::sort);
    }
}