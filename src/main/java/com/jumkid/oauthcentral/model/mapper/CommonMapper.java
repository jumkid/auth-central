package com.jumkid.oauthcentral.model.mapper;

import java.beans.PropertyDescriptor;

public interface CommonMapper<E, T> {

    T entityToDTO(E entity);

    E dtoToEntity(T dto);

    default void callSetter(Object obj, String fieldName, Object value){
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            pd.getWriteMethod().invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
