package com.epam.esm.model.dao;

public interface CertificateTagDao {
    void add(long var1, long var3);

    void delete(long var1, long var3);

    void deleteAllTags(long var1);

    void deleteByTagId(long var1);
}
