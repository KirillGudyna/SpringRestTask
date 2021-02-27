package com.epam.esm.model.entity;

public enum Permission {
    CERTIFICATE_READ("certificate:read"),
    CERTIFICATE_WRITE("certificate:write"),
    CERTIFICATE_DELETE("certificate:delete"),
    CERTIFICATE_UPDATE("certificate:update"),
    TAG_READ("tag:read"),
    TAG_WRITE("tag:write"),
    TAG_DELETE("tag:delete"),
    USER_READ("user:read"),
    USER_ORDER_READ("user:order_read"),
    USER_CREATE_ORDER("user:order_create"),
    USER_WIDELY_USED_TAG("user:widely_used_tag");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
