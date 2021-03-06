package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = TagDaoImpl.class)
class TagDaoTest {
    @Autowired
    private TagDao tagDao;

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(null, null, 16),
                Arguments.of(10, null, 10),
                Arguments.of(10, 10, 6),
                Arguments.of(10, 20, 0)
        );
    }

    static Stream<Arguments> argsFindByName() {
        return Stream.of(
                Arguments.of("Activity", true),
                Arguments.of("SomethingElse", false)
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    void findById(Long id, boolean result) {
        Optional<Tag> optional = tagDao.findById(id);
        assertEquals(result, optional.isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(Integer limit, Integer offset, int actualSize) {
        List<Tag> allTags = tagDao.findAll(limit, offset);
        assertEquals(actualSize, allTags.size());
    }

    @Test
    @DirtiesContext
    void add() {
        tagDao.add(new Tag("NewTag"));
        List<Tag> allTags = tagDao.findAll(null, null);
        assertEquals(17, allTags.size());
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    @DirtiesContext
    void delete(Long id, boolean result) {
        assertEquals(result, tagDao.delete(id));
    }

    @ParameterizedTest
    @MethodSource("argsFindByName")
    void findByName(String name, boolean result) {
        Optional<Tag> optional = tagDao.findByName(name);
        assertEquals(result, optional.isPresent());
    }
}