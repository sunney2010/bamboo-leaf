package com.bamboo.leaf.client.service;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:03
 */
public interface BambooLeafClient {
    /**
     * 获取序列
     *
     * @param namespace 命名空间
     * @return
     */
    public Long nextId(String namespace);
}
