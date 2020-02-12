package com.jumkid.oauthcentral.utils;

import java.util.Arrays;

public enum ClientDetailsField {
    ID("id", "id"), CLIENT_ID("clientId", "client_id"),
    RESOURCE_IDS("resourceIds", "resource_ids"),
    CLIENT_SECRET("clientSecret", "client_secret"), SCOPE("scope", "scope"),
    AUTHORIZED_GRANT_TYPES("authorizedGrantTypes", "authorized_grant_types"),
    WEB_SERVER_REDIRECT_URI("webServerRedirectUri", "web_server_redirect_uri"),
    AUTHORITIES("authorities", "authorities"),
    ACCESS_TOKEN_VALIDITY("accessTokenValidity", "access_token_validity"),
    REFRESH_TOKEN_VALIDITY("refreshTokenValidity", "refresh_token_validity"),
    ADDITIONAL_INFORMATION("additionalInformation", "additional_information"),
    AUTO_APPROVE("autoApprove", "autoapprove");

    private String value;
    private String columnName;

    ClientDetailsField(String value, String columnName) { this.value = value; this.columnName = columnName; }

    public String value() { return this.value; }
    public String columnName() { return this.columnName; }

    public static ClientDetailsField of(String value) {
        return Arrays.stream(values())
                .filter(fields -> fields.value.equals(value))
                .findFirst()
                .orElse(null);
    }

}
