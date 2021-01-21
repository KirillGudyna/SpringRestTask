package com.epam.esm.model.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Integer duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<Tag> tags;

    public GiftCertificate() {
    }

    public GiftCertificate(GiftCertificate certificate) {
        this.id = certificate.id;
        this.name = certificate.name;
        this.description = certificate.description;
        this.price = certificate.price;
        this.duration = certificate.duration;
        this.createDate = certificate.createDate;
        this.lastUpdateDate = certificate.lastUpdateDate;
        if (certificate.getTags() != null) {
            this.tags = new ArrayList();
            certificate.getTags().forEach((t) -> {
                this.tags.add(new Tag(t.getId(), t.getName()));
            });
        }

    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Timestamp getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return this.tags != null ? new ArrayList(this.tags) : null;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void clearAllTags() {
        this.tags = new ArrayList();
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            GiftCertificate that = (GiftCertificate)o;
            if (!Objects.equals(this.id, that.id)) {
                return false;
            } else if (!Objects.equals(this.name, that.name)) {
                return false;
            } else if (!Objects.equals(this.description, that.description)) {
                return false;
            } else if (!Objects.equals(this.price, that.price)) {
                return false;
            } else {
                return !Objects.equals(this.duration, that.duration) ? false : Objects.equals(this.tags, that.tags);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.price != null ? this.price.hashCode() : 0);
        result = 31 * result + (this.duration != null ? this.duration.hashCode() : 0);
        result = 31 * result + (this.tags != null ? this.tags.hashCode() : 0);
        return result;
    }
}