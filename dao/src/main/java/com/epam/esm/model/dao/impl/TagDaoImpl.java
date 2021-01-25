package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {
    public static final String SELECT_ID_NAME_FROM_TAG = "SELECT id, name FROM tag";
    public static final String SELECT_ID_NAME_FROM_TAG_WHERE_ID = "SELECT id, name FROM tag WHERE id = ?";
    public static final String SELECT_ID_NAME_FROM_TAG_WHERE_NAME = "SELECT id, name FROM tag WHERE name = ?";
    public static final String INSERT_INTO_TAG_NAME_VALUES = "INSERT INTO tag (name) VALUES (?)";
    public static final String DELETE_FROM_TAG_WHERE_ID = "DELETE FROM tag WHERE id = ?";
    public static final String SELECT_ALL_CERTIFICATE_TAGS = "SELECT id, name FROM gift_to_tag JOIN tag ON tag_id = id WHERE gift_certificate_id = ?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SELECT_ID_NAME_FROM_TAG);

        for (Map<String, Object> stringObjectMap : rows) {
            Tag tag = new Tag();
            tag.setId((Long) stringObjectMap.get("id"));
            tag.setName((String) stringObjectMap.get("name"));
            tags.add(tag);
        }

        return tags;
    }

    public Optional<Tag> findById(long id) {
        Optional<Tag> optional;
        try {
            Tag tag = jdbcTemplate.queryForObject(SELECT_ID_NAME_FROM_TAG_WHERE_ID, new TagDaoImpl.TagRowMapper(), id);
            optional = Optional.ofNullable(tag);
        } catch (EmptyResultDataAccessException var5) {
            optional = Optional.empty();
        }

        return optional;
    }

    public Optional<Tag> findByName(String name) {
        Optional<Tag> optional;
        try {
            Tag tag = this.jdbcTemplate.queryForObject(SELECT_ID_NAME_FROM_TAG_WHERE_NAME, new TagDaoImpl.TagRowMapper(), name);
            optional = Optional.ofNullable(tag);
        } catch (EmptyResultDataAccessException var4) {
            optional = Optional.empty();
        }

        return optional;
    }

    public Tag add(Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_TAG_NAME_VALUES, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);
        return this.findById(keyHolder.getKey().longValue()).get();
    }

    public boolean delete(long id) {
        return this.jdbcTemplate.update(DELETE_FROM_TAG_WHERE_ID, id) > 0;
    }

    public List<Tag> findAllTags(long certificateId) {
        List<Tag> tags = new ArrayList<>();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SELECT_ALL_CERTIFICATE_TAGS, certificateId);

        for (Map<String, Object> stringObjectMap : rows) {
            long tagId = (Long) stringObjectMap.get("id");
            String tagName = (String) stringObjectMap.get("name");
            tags.add(new Tag(tagId, tagName));
        }

        return tags;
    }

    private static class TagRowMapper implements RowMapper<Tag> {
        private TagRowMapper() {
        }

        public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setId(resultSet.getLong("id"));
            tag.setName(resultSet.getString("name"));
            return tag;
        }
    }
}