package com.bamboo.leaf.core.entity;

/**
 * Segment状态枚举
 * @author zhuzhi
 * @date 2020/11/19
 */
public enum ResultEnum {

    /**
     * 正常可用
     */
    NORMAL,
    /**
     * 需要去加载nextId
     */
    LOADING,
    /**
     * 超过maxId 不可用
     */
    OVER;

}
