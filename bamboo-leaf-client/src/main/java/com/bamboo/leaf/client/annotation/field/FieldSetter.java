package com.bamboo.leaf.client.annotation.field;

import com.bamboo.leaf.client.annotation.base.AbstractIdMetadata;
import com.bamboo.leaf.client.annotation.base.BambooSetter;
import com.bamboo.leaf.core.exception.BambooLeafException;

import java.lang.reflect.Field;

public class FieldSetter extends AbstractIdMetadata implements BambooSetter {
    public FieldSetter(Field idField) {
        super(idField);
    }

    @Override
    public void set(Object target, Object value) {
        try {
            getIdField().set(target, value);
        } catch (IllegalAccessException e) {
            throw new BambooLeafException(e);
        }
    }
}
