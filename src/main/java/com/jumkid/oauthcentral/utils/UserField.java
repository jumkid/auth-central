package com.jumkid.oauthcentral.utils;

import java.util.Arrays;

public enum UserField {

    USERNAME("username", "username"), PASSWORD("password", "password"),
    EMAIL("email", "email"), ACTIVE("active", "active");

    private String value;
    private String columnName;

    UserField(String value, String columnName) { this.value = value; this.columnName = columnName; }

    public String value() { return this.value; }
    public String columnName() { return this.columnName; }

    public static UserField of(String value) {
        return Arrays.stream(values())
                .filter(fields -> fields.value.equals(value))
                .findFirst()
                .orElse(null);
    }

}
