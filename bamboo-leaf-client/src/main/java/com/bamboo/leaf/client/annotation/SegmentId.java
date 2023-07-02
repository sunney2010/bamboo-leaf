package com.bamboo.leaf.client.annotation;

import java.lang.annotation.ElementType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SegmentId {
    /**
     * 全名空间
     */
    String namespace();

    /**
     * 模式.默认值:本地模式(Local)
     */
    String mode() default "Local";
}
