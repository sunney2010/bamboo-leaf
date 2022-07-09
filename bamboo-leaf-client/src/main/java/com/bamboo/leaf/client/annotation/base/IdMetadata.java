package com.bamboo.leaf.client.annotation.base;

import java.lang.reflect.Field;

//@Immutable
public interface IdMetadata {

    //CosIdDefinition getCosIdDefinition();

    Field getIdField();

    default Class<?> getIdDeclaringClass() {
        return getIdField().getDeclaringClass();
    }

    default Class<?> getIdType() {
        return getIdField().getType();
    }
}