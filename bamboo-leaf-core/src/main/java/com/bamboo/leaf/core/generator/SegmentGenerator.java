package com.bamboo.leaf.core.generator;

import java.util.List;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentGenerator {
    /**
     * get next id
     * @return
     */
    Long nextId();

    /**
     * get next id batch
     * @param batchSize
     * @return
     */
    List<Long> nextId(Integer batchSize);
}
