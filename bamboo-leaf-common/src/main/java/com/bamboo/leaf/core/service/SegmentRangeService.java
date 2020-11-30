package com.bamboo.leaf.core.service;

import com.bamboo.leaf.core.entity.SegmentRange;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentRangeService {

    /**
     * 根据namespace获取下一个namespace对象
     * 
     * @param namespace
     * @return
     */
    SegmentRange getNextSegmentRange(String namespace);

}
