package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;
import java.util.List;
import java.util.Optional;

public interface TagDao {
    Optional<Tag> findById(long var1);

    List<Tag> findAll();

    Tag add(Tag var1);

    boolean delete(long var1);

    Optional<Tag> findByName(String var1);

    List<Tag> findAllTags(long var1);
}