package com.bamboo.leaf.core.factory;

import com.bamboo.leaf.core.generator.SegmentGenerator;

/**
 * Segment通用接口
 *
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentGeneratorFactory {
    /**
     * 根据namespace创建id生成器
     *
     * @param namespace 命名空间
     * @param maxValue  最大值
     * @param mode      模式
     * @return
     */
    SegmentGenerator getSegmentGenerator(String namespace, long maxValue, String mode);


}
