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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
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
        return this.giftCertificateDao.findById(id);
    }

    public List<GiftCertificate> findAll() {
        return this.giftCertificateDao.findAll();
    }

    public GiftCertificate add(GiftCertificate certificate) {
        String currentDate = this.getCurrentDateIso();
        certificate.setCreateDate(Timestamp.valueOf(currentDate));
        certificate.setLastUpdateDate(Timestamp.valueOf(currentDate));
        return (GiftCertificate)this.transactionTemplate.execute((transactionStatus) -> {
            GiftCertificate added = this.giftCertificateDao.add(certificate);
            this.addTags(added, certificate.getTags());
            return added;
        });
    }

    private void addTags(GiftCertificate added, List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {
            Iterator var3 = tags.iterator();

            while(var3.hasNext()) {
                Tag tag = (Tag)var3.next();
                Tag addedTag = this.addTag(added.getId(), tag);
                added.addTag(addedTag);
            }
        }

    }

    private Tag addTag(long certificateId, Tag tag) {
        Optional<Tag> optionalTag = this.tagDao.findByName(tag.getName());
        Tag addedTag = (Tag)optionalTag.orElseGet(() -> {
            return this.tagDao.add(tag);
        });
        this.giftCertificateTagDao.add(certificateId, addedTag.getId());
        return addedTag;
    }

    public Optional<GiftCertificate> update(GiftCertificate certificate) {
        Optional<GiftCertificate> optional = this.giftCertificateDao.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate found = (GiftCertificate)optional.get();
            this.updateNotEmptyFields(certificate, found);
            certificate.setLastUpdateDate(Timestamp.valueOf(this.getCurrentDateIso()));
            GiftCertificate updated = (GiftCertificate)this.transactionTemplate.execute((transactionStatus) -> {
                GiftCertificate updating = this.giftCertificateDao.update(found);
                if (certificate.getTags() != null) {
                    this.giftCertificateTagDao.deleteAllTags(updating.getId());
                    updating.clearAllTags();
                    this.addTags(updating, certificate.getTags());
                }

                return updating;
            });
            optional = Optional.of(updated);
        }

        return optional;
    }

    private void updateNotEmptyFields(GiftCertificate source, GiftCertificate found) {
        if (source.getName() != null) {
            found.setName(source.getName());
        }

        if (source.getDescription() != null) {
            found.setDescription(source.getDescription());
        }

        if (source.getPrice() != null) {
            found.setPrice(source.getPrice());
        }

        if (source.getDuration() != null) {
            found.setDuration(source.getDuration());
        }

        if (source.getTags() != null) {
            found.setTags(source.getTags());
        }

    }

    public boolean delete(long id) {
        return (Boolean)this.transactionTemplate.execute((transactionStatus) -> {
            this.giftCertificateTagDao.deleteAllTags(id);
            return this.giftCertificateDao.delete(id);
        });
    }

    private String getCurrentDateIso() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    public List<GiftCertificate> findByTagName(String tagName, String sortType, String direction) {
        List<GiftCertificate> certificates = this.giftCertificateDao.findByTagName(tagName);
        this.sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    public List<GiftCertificate> findByName(String name, String sortType, String direction) {
        List<GiftCertificate> certificates = this.giftCertificateDao.findByName(name);
        this.sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    public List<GiftCertificate> findByDescription(String description, String sortType, String direction) {
        List<GiftCertificate> certificates = this.giftCertificateDao.findByDescription(description);
        this.sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    private void sortIfNecessary(List<GiftCertificate> certificates, String sortType, String direction) {
        Optional<Comparator<GiftCertificate>> optional = CertificateTagComparator.provide(sortType, direction);
        Objects.requireNonNull(certificates);
        optional.ifPresent(certificates::sort);
    }
}