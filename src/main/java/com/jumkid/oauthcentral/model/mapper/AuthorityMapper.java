package com.jumkid.oauthcentral.model.mapper;

import com.jumkid.oauthcentral.controller.dto.Authority;
import com.jumkid.oauthcentral.model.AuthorityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface AuthorityMapper extends CommonMapper<AuthorityEntity, Authority> {
}
