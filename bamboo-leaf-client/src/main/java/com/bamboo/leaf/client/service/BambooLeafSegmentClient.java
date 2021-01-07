package com.bamboo.leaf.client.service;

/**
 * @description: Segment算法客户端接口
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:03
 */
public interface BambooLeafSegmentClient {
    /**
     * 获取segment算法序列
     *
     * @param namespace 命名空间
     * @return
     */
    public Long segmentId(String namespace);

    /**
     * 获取segment算法序列
     *
     * @param namespace 命名空间
     * @return
     */
    public Long dateSegmentId(String namespace);

    /**
     * 获取segment算法序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @return
     */
    public String dateSegmentId(String namespace, String prefix);

    /**
     * 获取segment算法序列
     *
     * @param namespace 命名空间
     * @return
     */
    public Long timeSegmentId(String namespace);

    /**
     * 获取segment算法序列
     *
     * @param namespace 命名空间
     * @param prefix    前缀
     * @return
     */
    public String timeSegmentId(String namespace, String prefix);
}
