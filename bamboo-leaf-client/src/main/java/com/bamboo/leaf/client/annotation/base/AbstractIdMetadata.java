package com.bamboo.leaf.client.annotation.base;

import java.lang.reflect.Field;

public class AbstractIdMetadata implements IdMetadata {
    private final Field idField;

    public AbstractIdMetadata(Field idField) {
        this.idField = idField;
    }


    @Override
    public Field getIdField() {
        return idField;
    }
}
