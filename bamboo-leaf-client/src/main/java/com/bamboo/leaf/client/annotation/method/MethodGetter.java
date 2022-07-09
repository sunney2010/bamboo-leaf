package com.bamboo.leaf.client.annotation.method;

import com.bamboo.leaf.client.annotation.base.BambooGetter;
import com.bamboo.leaf.core.exception.BambooLeafException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodGetter implements BambooGetter {
    private final Method getter;

    public MethodGetter(Method getter) {
        this.getter = getter;
    }

    @Override
    public Object get(Object target) {
        try {
            return this.getter.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BambooLeafException(e);
        }
    }
}
