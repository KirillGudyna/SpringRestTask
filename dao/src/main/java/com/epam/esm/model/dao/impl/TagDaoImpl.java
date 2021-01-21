package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {
    public static final String SELECT_ID_NAME_FROM_TAG = "SELECT id, name FROM tag";
    public static final String SELECT_ID_NAME_FROM_TAG_WHERE_ID = "SELECT id, name FROM tag WHERE id = ?";
    public static final String SELECT_ID_NAME_FROM_TAG_WHERE_NAME = "SELECT id, name FROM tag WHERE name = ?";
    public static final String INSERT_INTO_TAG_NAME_VALUES = "INSERT INTO tag (name) VALUES (?)";
    public static final String DELETE_FROM_TAG_WHERE_ID = "DELETE FROM tag WHERE id = ?";
    public static final String SQL_ALL_CERTIFICATE_TAGS = "SELECT id, name FROM gift_to_tag JOIN tag ON tag_id = id WHERE gift_certificate_id = ?";
    private JdbcTemplate jdbcTemplate;

    public TagDaoImpl() {
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SELECT_ID_NAME_FROM_TAG);
        Iterator var3 = rows.iterator();

        while(var3.hasNext()) {
            Map<String, Object> row = (Map)var3.next();
            Tag tag = new Tag();
            tag.setId((Long)row.get("id"));
            tag.setName((String)row.get("name"));
            tags.add(tag);
        }

        return tags;
    }

    public Optional<Tag> findById(long id) {
        Optional optional;
        try {
            Tag tag = (Tag)this.jdbcTemplate.queryForObject(SELECT_ID_NAME_FROM_TAG_WHERE_ID, new TagDaoImpl.TagRowMapper(), new Object[]{id});
            optional = Optional.of(tag);
        } catch (EmptyResultDataAccessException var5) {
            optional = Optional.empty();
        }

        return optional;
    }

    public Optional<Tag> findByName(String name) {
        Optional optional;
        try {
            Tag tag = (Tag)this.jdbcTemplate.queryForObject(SELECT_ID_NAME_FROM_TAG_WHERE_NAME, new TagDaoImpl.TagRowMapper(), new Object[]{name});
            optional = Optional.of(tag);
        } catch (EmptyResultDataAccessException var4) {
            optional = Optional.empty();
        }

        return optional;
    }

    public Tag add(Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_TAG_NAME_VALUES, 1);
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);
        return (Tag)this.findById(keyHolder.getKey().longValue()).get();
    }

    public boolean delete(long id) {
        return this.jdbcTemplate.update(DELETE_FROM_TAG_WHERE_ID, new Object[]{id}) > 0;
    }

    public List<Tag> findAllTags(long certificateId) {
        List<Tag> tags = new ArrayList();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SQL_ALL_CERTIFICATE_TAGS, new Object[]{certificateId});
        Iterator var5 = rows.iterator();

        while(var5.hasNext()) {
            Map<String, Object> row = (Map)var5.next();
            long tagId = (Long)row.get("id");
            String tagName = (String)row.get("name");
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