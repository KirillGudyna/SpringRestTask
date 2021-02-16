package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.model.util.DateTimeUtil;
import com.epam.esm.util.DtoWrapper;
import com.epam.esm.util.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final String DASH = "-";
    private static final String UNDER_SCOPE = "_";

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;

    public GiftCertificateServiceImpl() {
    }

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
    }

    @Autowired
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }


    @Override
    public Optional<GiftCertificateDto> findById(long id) {
        return giftCertificateDao.findById(id).map(DtoWrapper::toGiftCertificateDto);
    }

    @Override
    public List<GiftCertificateDto> findAll(String name,
                                            String description,
                                            String tagNames,
                                            String sortType,
                                            String direction,
                                            Integer limit,
                                            Integer offset) {
        if (sortType != null) {
            sortType = sortType.replace(DASH, UNDER_SCOPE);
        }
        return giftCertificateDao.findAll(name, description, tagNames != null ? tagNames.split(",") : null, sortType, direction, limit, offset)
                .stream()
                .map(DtoWrapper::toGiftCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GiftCertificateDto add(GiftCertificateDto certificateDto) {
        String currentDate = DateTimeUtil.getCurrentDateIso();
        certificateDto.setCreateDate(currentDate);
        certificateDto.setLastUpdateDate(currentDate);
        GiftCertificate certificate = EntityWrapper.toGiftCertificate(certificateDto);
        findTagsInDB(certificate);
        return DtoWrapper.toGiftCertificateDto(giftCertificateDao.add(certificate));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<GiftCertificateDto> update(GiftCertificateDto certificate) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate foundCertificate = optional.get();
            updateNotEmptyFields(certificate, foundCertificate);
            certificate.setLastUpdateDate(DateTimeUtil.getCurrentDateIso());
            GiftCertificate updatingCertificate = giftCertificateDao.update(foundCertificate);
            optional = Optional.of(updatingCertificate);
        }

        return optional.map(DtoWrapper::toGiftCertificateDto);
    }

    @Override
    public boolean delete(long id) {
        return giftCertificateDao.delete(id);
    }

    private void findTagsInDB(GiftCertificate certificate) {
        List<Tag> tags = certificate.getTags();
        if (tags != null) {
            ListIterator<Tag> iterator = tags.listIterator();
            while (iterator.hasNext()) {
                Tag tag = iterator.next();
                Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
                optionalTag.ifPresent(iterator::set);
            }
        }
    }

    private void updateNotEmptyFields(GiftCertificateDto source, GiftCertificate foundCertificate) {
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
            GiftCertificate certificate = EntityWrapper.toGiftCertificate(source);
            findTagsInDB(certificate);
            foundCertificate.setTags(certificate.getTags());
        }

    }
}