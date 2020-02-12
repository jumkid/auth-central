package com.jumkid.oauthcentral.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@EqualsAndHashCode(of = {"username"})
public class User {

    public static String ENTITY_NAME = "USER";

    @NotBlank(message = "username is required")
    @Size(max = 50)
    @JsonProperty("username")
    private String username;

    @Size(max = 120)
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "email is required")
    @JsonProperty("email")
    private String email;

    @JsonProperty("active")
    private boolean active;

}
