package com.jumkid.oauthcentral.model.mapper;

import com.jumkid.oauthcentral.controller.dto.ClientDetails;
import com.jumkid.oauthcentral.model.ClientDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel="spring")
public interface ListClientDetailsMapper {

    default List<ClientDetails> entitiesToDTOS(List<ClientDetailsEntity> entities) {
        if (entities != null && !entities.isEmpty()) {
            ClientDetailsMapper clientDetailsMapperMapper = Mappers.getMapper(ClientDetailsMapper.class);
            return entities.stream()
                    .map(entity -> buildDto(entity, clientDetailsMapperMapper))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private ClientDetails buildDto(ClientDetailsEntity entity, ClientDetailsMapper clientDetailsMapperMapper) {
        ClientDetails dto = clientDetailsMapperMapper.entityToDTO(entity);
        dto.setClientSecret(null);
        return dto;
    }

}
