package com.jumkid.oauthcentral.utils;

public enum UserRole {

    USER("USER"),
    SUPER_ADMIN("SUPER_ADMIN");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}