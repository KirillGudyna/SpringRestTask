package com.epam.esm.model.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum  Role {
    USER(Arrays.asList(
            Permission.CERTIFICATE_READ,
            Permission.TAG_READ,
            Permission.USER_WIDELY_USED_TAG,
            Permission.USER_CREATE_ORDER
    )),

    ADMIN(Arrays.asList(
            Permission.CERTIFICATE_READ,
            Permission.CERTIFICATE_DELETE,
            Permission.CERTIFICATE_UPDATE,
            Permission.CERTIFICATE_WRITE,
            Permission.TAG_READ,
            Permission.TAG_DELETE,
            Permission.TAG_WRITE,
            Permission.USER_READ,
            Permission.USER_ORDER_READ,
            Permission.USER_WIDELY_USED_TAG
    ));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthority() {
        return getPermissions().stream().
                map(permission -> new SimpleGrantedAuthority(permission.getPermission())).
                collect(Collectors.toList());
    }
}
