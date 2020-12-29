package com.bamboo.leaf.core.factory;

import com.bamboo.leaf.core.generator.SegmentGenerator;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentGeneratorFactory {
    /**
     * 根据namespace创建id生成器
     *
     * @param namespace
     * @param maxValue
     * @return
     */
    SegmentGenerator getSegmentGenerator(String namespace, long maxValue);


}
