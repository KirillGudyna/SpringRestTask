package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    Optional<Tag> findById(long id);

    List<Tag> findAll(Integer limit, Integer offset);

    Tag add(Tag tag);

    boolean delete(long id);

    Optional<Tag> findByName(String name);

    Class<Tag> getEntityClass();
}
