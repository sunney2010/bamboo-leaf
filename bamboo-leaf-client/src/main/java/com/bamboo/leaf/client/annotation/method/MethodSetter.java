package com.bamboo.leaf.client.annotation.method;

import com.bamboo.leaf.client.annotation.base.BambooSetter;
import com.bamboo.leaf.core.exception.BambooLeafException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodSetter implements BambooSetter {
    private final Method setter;

    public MethodSetter(Method setter) {
        this.setter = setter;
    }

    @Override
    public void set(Object target, Object value) {
        try {
            this.setter.invoke(target, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BambooLeafException(e);
        }
    }
}
