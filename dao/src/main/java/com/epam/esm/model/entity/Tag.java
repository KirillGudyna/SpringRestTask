package com.epam.esm.model.entity;

import java.util.Objects;

public class Tag {
    private long id;
    private String name;

    public Tag() {
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Tag tag = (Tag) o;
            return this.id == tag.id && Objects.equals(this.name, tag.name);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = (int) (this.id ^ this.id >>> 32);
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        return result;
    }
}