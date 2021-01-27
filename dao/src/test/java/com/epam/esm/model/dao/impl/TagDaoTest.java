package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;

class TagDaoTest {
    private TagDao tagDao;
    private EmbeddedDatabase embeddedDatabase;


    @BeforeEach
    void setUp() {
        this.embeddedDatabase = (new EmbeddedDatabaseBuilder()).addDefaultScripts().setType(EmbeddedDatabaseType.H2).build();
        tagDao = new TagDaoImpl();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.embeddedDatabase);
        ((TagDaoImpl) this.tagDao).setJdbcTemplate(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        this.embeddedDatabase.shutdown();
    }

    @Test
    void findByIdExist() {
        Optional<Tag> optional = tagDao.findById(1L);
        Assertions.assertTrue(optional.isPresent() && (optional.get()).getName().equals("Activity"));
    }

    @Test
    void findByIdNotExist() {
        Optional<Tag> optional = tagDao.findById(999L);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void findAll() {
        List<Tag> allTags = tagDao.findAll();
        Assertions.assertEquals(10, allTags.size());
    }

    @Test
    void add() {
        this.tagDao.add(new Tag(255L, "NewTag"));
        List<Tag> allTags = tagDao.findAll();
        Assertions.assertEquals(11, allTags.size());
    }

    @Test
    void delete() {
        this.tagDao.delete(1L);
        Assertions.assertFalse(tagDao.findById(1L).isPresent());
    }

    @Test
    void findByNameExist() {
        Optional<Tag> optional = tagDao.findByName("Activity");
        Assertions.assertTrue(optional.isPresent());
    }

    @Test
    void findByNameNotExist() {
        Optional<Tag> optional = tagDao.findByName("SomethingElse");
        Assertions.assertFalse(optional.isPresent());
    }
}