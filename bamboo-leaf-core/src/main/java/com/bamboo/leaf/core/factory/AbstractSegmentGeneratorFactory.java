package com.bamboo.leaf.core.factory;

import com.bamboo.leaf.core.generator.SegmentGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Segment算法抽象
 *
 * @author zhuzhi
 * @date 2020/11/19
 */
public abstract class AbstractSegmentGeneratorFactory implements SegmentGeneratorFactory {

    private static ConcurrentHashMap<String, SegmentGenerator> generatorMap = new ConcurrentHashMap<>();

    @Override
    public SegmentGenerator getSegmentGenerator(String namespace, long maxValue, String mode) {
        if (generatorMap.containsKey(namespace)) {
            return generatorMap.get(namespace);
        }
        synchronized (this) {
            if (generatorMap.containsKey(namespace)) {
                return generatorMap.get(namespace);
            }
            SegmentGenerator generator = createSegmentGenerator(namespace, maxValue, mode);
            generatorMap.put(namespace, generator);
            return generator;
        }
    }


    /**
     * 根据namespace创建id生成器
     *
     * @param namespace 命名空间
     * @param maxValue  最大值
     * @param mode      模式
     * @return
     */
    protected abstract SegmentGenerator createSegmentGenerator(String namespace, long maxValue, String mode);


}
