package com.bamboo.leaf.common.generator;

import java.util.List;

/**
 * @author du_imba
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
