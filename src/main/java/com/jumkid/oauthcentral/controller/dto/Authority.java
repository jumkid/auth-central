package com.jumkid.oauthcentral.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class Authority {

    public static final String ENTITY_NAME = "AUTHORITY";

    private Integer id;

    @NotBlank(message = "username is required")
    @Size(max = 50)
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "role is required")
    @Size(max = 50)
    @JsonProperty("role")
    private String role;
}
