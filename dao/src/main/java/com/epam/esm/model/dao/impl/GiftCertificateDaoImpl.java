package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String SQL_SELECT_ALL_CERTIFICATES = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate";
    private static final String SQL_SELECT_ALL_CERTIFICATES_BY_ID = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate\nWHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE gift_certificate SET NAME = ?, description = ?, price = ?, duration = ?, last_update_date = ? \nWHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_CERTIFICATES_BY_TAG_NAME = "SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date\nFROM gift_certificate JOIN gift_to_tag ON gift_certificate.id=gift_certificate_id JOIN tag ON tag.id=tag_id\nWHERE tag.name=?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id, name, description, price, duration, create_date, last_update_date\nFROM gift_certificate WHERE name LIKE ?";
    private static final String SQL_SELECT_BY_DESCRIPTION = "SELECT id, name, description, price, duration, create_date, last_update_date\nFROM gift_certificate WHERE description LIKE ?";
    private static final String PERCENT = "%";
    private JdbcTemplate jdbcTemplate;
    private TagDao tagDao;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setGiftCertificateTagDao(TagDaoImpl tagDao) {
        this.tagDao = tagDao;
    }

    public Optional<GiftCertificate> findById(long id) {
        Optional optional;
        try {
            GiftCertificate giftCertificate = this.jdbcTemplate.queryForObject(SQL_SELECT_ALL_CERTIFICATES_BY_ID, new GiftCertificateRowMapper(), new Object[]{id});
            List<Tag> tags = this.tagDao.findAllTags(id);
            giftCertificate.setTags(tags);
            optional = Optional.of(giftCertificate);
        } catch (EmptyResultDataAccessException var6) {
            optional = Optional.empty();
        }

        return optional;
    }

    public List<GiftCertificate> findAll() {
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SQL_SELECT_ALL_CERTIFICATES);
        return this.getGiftCertificates(rows);
    }

    public GiftCertificate add(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, 1);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getPrice() != null ? entity.getPrice() : 0);
            ps.setInt(4, entity.getDuration() != null ? entity.getDuration() : 0);
            ps.setTimestamp(5, entity.getCreateDate());
            ps.setTimestamp(6, entity.getLastUpdateDate());
            return ps;
        }, keyHolder);
        return this.findById(keyHolder.getKey().longValue()).get();
    }

    public GiftCertificate update(GiftCertificate entity) {
        this.jdbcTemplate.update(SQL_UPDATE, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getDuration(), entity.getLastUpdateDate(), entity.getId());
        return this.findById(entity.getId()).get();
    }

    public boolean delete(long id) {
        return this.jdbcTemplate.update(SQL_DELETE, id) > 0;
    }

    public List<GiftCertificate> findByTagName(String tagName) {
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SQL_SELECT_CERTIFICATES_BY_TAG_NAME, tagName);
        return this.getGiftCertificates(rows);
    }

    public List<GiftCertificate> findByName(String name) {
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SQL_SELECT_BY_NAME, PERCENT + name + PERCENT);
        return this.getGiftCertificates(rows);
    }

    public List<GiftCertificate> findByDescription(String description) {
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SQL_SELECT_BY_DESCRIPTION, PERCENT + description + PERCENT);
        return this.getGiftCertificates(rows);
    }

    private List<GiftCertificate> getGiftCertificates(List<Map<String, Object>> rows) {
        List<GiftCertificate> certificates = new ArrayList();
        Iterator var3 = rows.iterator();

        while (var3.hasNext()) {
            Map<String, Object> row = (Map) var3.next();
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId((Long) row.get("id"));
            giftCertificate.setName((String) row.get("name"));
            giftCertificate.setDescription((String) row.get("description"));
            giftCertificate.setPrice((Integer) row.get("price"));
            giftCertificate.setDuration((Integer) row.get("duration"));
            giftCertificate.setCreateDate((Timestamp) row.get("create_date"));
            giftCertificate.setLastUpdateDate((Timestamp) row.get("last_update_date"));
            List<Tag> tags = this.tagDao.findAllTags(giftCertificate.getId());
            giftCertificate.setTags(tags);
            certificates.add(giftCertificate);
        }

        return certificates;
    }

    private class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
        private GiftCertificateRowMapper() {
        }

        public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
            GiftCertificate certificate = new GiftCertificate();
            certificate.setId(resultSet.getLong("id"));
            certificate.setName(resultSet.getString("name"));
            certificate.setDescription(resultSet.getString("description"));
            certificate.setPrice(resultSet.getInt("price"));
            certificate.setDuration(resultSet.getInt("duration"));
            certificate.setCreateDate(Timestamp.valueOf(resultSet.getString("create_date")));
            certificate.setLastUpdateDate(Timestamp.valueOf(resultSet.getString("last_update_date")));
            return certificate;
        }
    }
}