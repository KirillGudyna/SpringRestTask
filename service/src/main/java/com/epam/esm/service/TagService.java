package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import java.util.List;
import java.util.Optional;

public interface TagService {
    Optional<TagDto> findById(long id);

    List<TagDto> findAll(Integer limit, Integer offset);

    TagDto add(TagDto tagDto);

    boolean delete(long id);
}