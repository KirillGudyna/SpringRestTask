package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.DtoWrapper;
import com.epam.esm.util.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    public TagServiceImpl() {
    }

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;

    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<TagDto> findById(long id) {
        return tagDao.findById(id).map(DtoWrapper::toTagDto);
    }

    @Override
    public List<TagDto> findAll(Integer limit, Integer offset) {
        return tagDao.findAll(limit, offset)
                .stream()
                .map(DtoWrapper::toTagDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TagDto add(TagDto tagDto) {
        Optional<Tag> optionalTag = tagDao.findById(tagDto.getId());
        return optionalTag.map(DtoWrapper::toTagDto).orElseGet(() -> DtoWrapper.toTagDto(tagDao.add(EntityWrapper.toTag(tagDto))));
    }

    @Override
    public boolean delete(long id) {
        return tagDao.delete(id);
    }
}