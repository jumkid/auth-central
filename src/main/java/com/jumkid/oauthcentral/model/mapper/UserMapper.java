package com.jumkid.oauthcentral.model.mapper;

import com.jumkid.oauthcentral.controller.dto.User;
import com.jumkid.oauthcentral.model.ClientDetailsEntity;
import com.jumkid.oauthcentral.model.UserEntity;
import com.jumkid.oauthcentral.utils.ClientDetailsField;
import com.jumkid.oauthcentral.utils.UserField;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.beans.PropertyDescriptor;
import java.util.Map;

@Mapper(componentModel="spring")
public interface UserMapper extends CommonMapper<UserEntity, User> {

    @AfterMapping
    default void blindPassword(UserEntity entity, @MappingTarget User.UserBuilder builder) {
        builder.password(null);
    }

    default UserEntity updatesMapToEntity(Map<String, Object> updatesMap, UserEntity entity) {
        updatesMap.entrySet().forEach(entry -> patchEntity(entry, entity));
        return entity;
    }

    private void patchEntity(Map.Entry entry, UserEntity entity) {
        UserField field = UserField.of(((String)entry.getKey()));
        if (entry.getValue() != null && field != null && field != UserField.USERNAME) {
            callSetter(entity, (String)entry.getKey(), entry.getValue());
        }
    }

}
