package com.jumkid.oauthcentral.model.mapper;

import com.jumkid.oauthcentral.controller.dto.Authority;
import com.jumkid.oauthcentral.model.AuthorityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel="spring")
public interface ListAuthorityMapper {

    default List<Authority> entitiesToDTOS(List<AuthorityEntity> entities) {
        if (entities != null && !entities.isEmpty()) {
            AuthorityMapper authorityMapper = Mappers.getMapper(AuthorityMapper.class);
            return entities.stream()
                    .map(authorityMapper::entityToDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
