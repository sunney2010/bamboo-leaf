package com.bamboo.leaf.common.factory;

import com.bamboo.leaf.common.generator.SegmentGenerator;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentGeneratorFactory {
    /**
     * 根据namespace创建id生成器
     * @param namespace
     * @return
     */
    SegmentGenerator getSegmentGenerator(String namespace);
}
