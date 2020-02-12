package com.jumkid.oauthcentral.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class ClientDetails {

    public static final String ENTITY_NAME = "CLIENT_DETAILS";

    private Integer id;

    @NotBlank(message = "clientId is required")
    @Size(max = 255)
    @JsonProperty("clientId")
    private String clientId;

    @Size(max = 255)
    @JsonProperty("resourceIds")
    private String resourceIds;

    @Size(max = 255)
    @JsonProperty("clientSecret")
    private String clientSecret;

    @Size(max = 255)
    @JsonProperty("scope")
    private String scope;

    @Size(max = 255)
    @JsonProperty("authorizedGrantTypes")
    private String authorizedGrantTypes;

    @Size(max = 255)
    @JsonProperty("webServerRedirectUri")
    private String webServerRedirectUri;

    @Size(max = 255)
    @JsonProperty("authorities")
    private String authorities;

    @Min(0)
    @JsonProperty("accessTokenValidity")
    private Integer accessTokenValidity;

    @Min(0)
    @JsonProperty("refreshTokenValidity")
    private Integer refreshTokenValidity;

    @Size(max = 5000)
    @JsonProperty("additionalInformation")
    private String additionalInformation;

    @JsonProperty("autoApprove")
    private boolean autoApprove;

}
