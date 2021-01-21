package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import java.util.List;
import java.util.Optional;

public interface TagService {
    Optional<Tag> findById(long var1);

    List<Tag> findAll();

    Tag add(Tag var1);

    boolean delete(long var1);
}