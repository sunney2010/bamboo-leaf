package com.bamboo.leaf.core.service;

import com.bamboo.leaf.core.entity.SegmentRange;

/**
 * Segment接口获取序列段
 *
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentService {

    /**
     * 根据namespace获取下一个namespace对象
     *
     * @param namespace 命名空间
     * @param maxVal    序列的最大值
     * @param step      动态步长
     * @return
     */
    SegmentRange getNextSegmentRange(String namespace, long maxVal, Integer step);

}
