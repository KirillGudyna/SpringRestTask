package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.util.ColumnName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private static final String SQL_FIND_ALL_JOIN_TABLES =
            "SELECT DISTINCT g.id, g.name, g.description, g.price, g.duration, g.create_date, g.last_update_date\n" +
                    "FROM gift_certificate AS g JOIN gift_to_tag as ct ON g.id=ct.gift_certificate_id " +
                    "JOIN tag AS t ON ct.tag_id=t.id \n";

    private static final String PERCENT = "%";
    private static final String WHERE = "WHERE ";
    private static final String NAME_LIKE = "g.name LIKE ? ";
    private static final String DESCRIPTION_LIKE = "description LIKE ? ";
    private static final String TAG_NAME_EQUALS = "t.name = ? ";
    private static final String AND = "AND ";
    private static final String ORDER_BY = "ORDER BY ";
    private static final String SPACE = " ";

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
        Optional<GiftCertificate> optional;
        try {
            GiftCertificate giftCertificate = jdbcTemplate.queryForObject(SQL_SELECT_ALL_CERTIFICATES_BY_ID, new GiftCertificateRowMapper(), id);
            List<Tag> tags = this.tagDao.findAllTags(id);
            giftCertificate.setTags(tags);
            optional = Optional.of(giftCertificate);
        } catch (EmptyResultDataAccessException var6) {
            optional = Optional.empty();
        }

        return optional;
    }

    @Override
    public List<GiftCertificate> findAll(String name,
                                         String description,
                                         String tagName,
                                         String sortType,
                                         String direction) {
        StringBuilder sb;
        List<String> parameterList = new ArrayList<>();
        if (name != null || description != null || tagName != null) {
            sb = new StringBuilder(SQL_FIND_ALL_JOIN_TABLES);
            sb.append(WHERE);
        } else {
            sb = new StringBuilder(SQL_SELECT_ALL_CERTIFICATES);
        }
        if (name != null) {
            sb.append(NAME_LIKE);
            parameterList.add(PERCENT + name + PERCENT);
        }
        if (description != null) {
            sb.append(parameterList.isEmpty() ? DESCRIPTION_LIKE : AND + DESCRIPTION_LIKE);
            parameterList.add(PERCENT + description + PERCENT);
        }
        if (tagName != null) {
            sb.append(parameterList.isEmpty() ? TAG_NAME_EQUALS : AND + TAG_NAME_EQUALS);
            parameterList.add(tagName);
        }
        if (sortType != null) {
            sb.append(ORDER_BY).append(sortType);
        }
        if (direction != null) {
            sb.append(SPACE).append(direction);
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sb.toString(), parameterList.toArray());
        return getGiftCertificates(rows);
    }

    public GiftCertificate add(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getPrice() != null ? entity.getPrice() : 0);
            ps.setInt(4, entity.getDuration() != null ? entity.getDuration() : 0);
            ps.setString(5, entity.getCreateDate());
            ps.setString(6, entity.getLastUpdateDate());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue()).get();
    }

    public GiftCertificate update(GiftCertificate entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getName(), entity.getDescription(), entity.getPrice(), entity.getDuration(), entity.getLastUpdateDate(), entity.getId());
        return findById(entity.getId()).get();
    }

    public boolean delete(long id) {
        return jdbcTemplate.update(SQL_DELETE, id) > 0;
    }

    public List<GiftCertificate> findByTagName(String tagName) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_CERTIFICATES_BY_TAG_NAME, tagName);
        return this.getGiftCertificates(rows);
    }

    public List<GiftCertificate> findByName(String name) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_NAME, PERCENT + name + PERCENT);
        return this.getGiftCertificates(rows);
    }

    public List<GiftCertificate> findByDescription(String description) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_DESCRIPTION, PERCENT + description + PERCENT);
        return this.getGiftCertificates(rows);
    }

    private List<GiftCertificate> getGiftCertificates(List<Map<String, Object>> rows) {
        List<GiftCertificate> certificates = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId((Long) row.get("id"));
            giftCertificate.setName((String) row.get("name"));
            giftCertificate.setDescription((String) row.get("description"));
            giftCertificate.setPrice((Integer) row.get("price"));
            giftCertificate.setDuration((Integer) row.get("duration"));
            giftCertificate.setCreateDate((String) row.get("create_date"));
            giftCertificate.setLastUpdateDate((String) row.get("last_update_date"));
            List<Tag> tags = this.tagDao.findAllTags(giftCertificate.getId());
            giftCertificate.setTags(tags);
            certificates.add(giftCertificate);
        }

        return certificates;
    }

    private static class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
        private GiftCertificateRowMapper() {
        }

        public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
            GiftCertificate certificate = new GiftCertificate();
            certificate.setId(resultSet.getLong(ColumnName.GIFT_CERTIFICATE_ID));
            certificate.setName(resultSet.getString(ColumnName.GIFT_CERTIFICATE_NAME));
            certificate.setDescription(resultSet.getString(ColumnName.GIFT_CERTIFICATE_DESCRIPTION));
            certificate.setPrice(resultSet.getInt(ColumnName.GIFT_CERTIFICATE_PRICE));
            certificate.setDuration(resultSet.getInt(ColumnName.GIFT_CERTIFICATE_DURATION));
            certificate.setCreateDate(resultSet.getString(ColumnName.GIFT_CERTIFICATE_CREATE_DATE));
            certificate.setLastUpdateDate(resultSet.getString(ColumnName.GIFT_CERTIFICATE_LAST_UPDATE_DATE));
            return certificate;
        }
    }
}