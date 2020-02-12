package com.jumkid.oauthcentral.model.mapper;

import com.jumkid.oauthcentral.controller.dto.ClientDetails;
import com.jumkid.oauthcentral.model.ClientDetailsEntity;
import com.jumkid.oauthcentral.utils.ClientDetailsField;
import org.mapstruct.*;

import java.beans.PropertyDescriptor;
import java.util.Map;

@Mapper(componentModel="spring")
public interface ClientDetailsMapper extends CommonMapper<ClientDetailsEntity, ClientDetails>{

    @AfterMapping
    default void blindClientSecrect(ClientDetailsEntity entity, @MappingTarget ClientDetails.ClientDetailsBuilder builder) {
        builder.clientSecret(null);
    }

    default ClientDetailsEntity updatesMapToEntity(Map<String, Object> updatesMap, ClientDetailsEntity entity) {
        updatesMap.entrySet().forEach(entry -> patchEntity(entry, entity));
        return entity;
    }

    private void patchEntity(Map.Entry entry, ClientDetailsEntity entity) {
        ClientDetailsField field = ClientDetailsField.of(((String)entry.getKey()));
        if (entry.getValue() != null && field != null && field != ClientDetailsField.ID) {
            callSetter(entity, (String)entry.getKey(), entry.getValue());
        }
    }

}
