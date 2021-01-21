package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.CertificateTagDao;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CertificateTagDaoImpl implements CertificateTagDao {
    private static final String SQL_INSERT = "INSERT INTO certificate_tag (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM certificate_tag WHERE gift_certificate_id = ? AND tag_id = ?";
    private static final String SQL_ALL_CERTIFICATE_TAGS_ID = "SELECT tag_id FROM certificate_tag WHERE gift_certificate_id = ?";
    private static final String SQL_DELETE_BY_TAG_ID = "DELETE FROM certificate_tag WHERE tag_id = ?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(long certificateId, long tagId) {
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{certificateId, tagId});
    }

    public void delete(long certificateId, long tagId) {
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{certificateId, tagId});
    }

    public void deleteAllTags(long certificateId) {
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(SQL_ALL_CERTIFICATE_TAGS_ID, new Object[]{certificateId});
        Iterator var4 = rows.iterator();

        while(var4.hasNext()) {
            Map<String, Object> row = (Map)var4.next();
            long tagId = (Long)row.get("tag_id");
            this.delete(certificateId, tagId);
        }

    }

    public void deleteByTagId(long tagId) {
        this.jdbcTemplate.update(SQL_DELETE_BY_TAG_ID, new Object[]{tagId});
    }
}
