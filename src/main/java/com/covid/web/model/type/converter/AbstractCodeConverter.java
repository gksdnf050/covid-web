package com.covid.web.model.type.converter;

import com.covid.web.model.type.Code;
import com.covid.web.util.EnumUtils;

import javax.persistence.AttributeConverter;

public abstract class AbstractCodeConverter<E extends Enum<E> & Code<T>, T> implements AttributeConverter<E, T> {

    private final Class<E> type;

    public AbstractCodeConverter(Class<E> type) {
        this.type = type;
    }

    @Override
    public T convertToDatabaseColumn(E attribute) {
        if(attribute == null)
            return null;

        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(T dbData) {
        return EnumUtils.lookupFromCode(type, dbData);
    }
}
