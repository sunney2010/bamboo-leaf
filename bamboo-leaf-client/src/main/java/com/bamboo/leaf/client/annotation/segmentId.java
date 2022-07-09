package com.bamboo.leaf.client.annotation;


public @interface sequence {
    /**
     * 全名空间
     */
    String namespace();

    /**
     * 模式.默认值:本地模式(Local)
     */
    String mode() default "Local";
}
