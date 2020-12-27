package com.bamboo.leaf.core.generator;

import java.util.List;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentGenerator {
    /**
     * get next id
     *
     * @return
     */
    Long nextSegmentId();

    /**
     * get next id
     *
     * @param maxValue 最大值
     * @return
     */
    String nextSegmentIdFixed(long maxValue);


    /**
     * get next id batch
     *
     * @param batchSize
     * @return
     */
    List<Long> nextSegmentId(Integer batchSize);
}
