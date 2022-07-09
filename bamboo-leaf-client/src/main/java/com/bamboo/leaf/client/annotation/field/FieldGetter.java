package com.bamboo.leaf.client.annotation.field;

import com.bamboo.leaf.client.annotation.base.AbstractIdMetadata;
import com.bamboo.leaf.client.annotation.base.BambooGetter;
import com.bamboo.leaf.core.exception.BambooLeafException;

import java.lang.reflect.Field;

public class FieldGetter extends AbstractIdMetadata implements BambooGetter {

    public FieldGetter(Field idField) {
        super(idField);
    }

    @Override
    public Object get(Object target) {
        try {
            return getIdField().get(target);
        } catch (IllegalAccessException e) {
            throw new BambooLeafException(e);
        }
    }
}
