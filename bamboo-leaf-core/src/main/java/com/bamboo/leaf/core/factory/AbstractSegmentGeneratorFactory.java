package com.bamboo.leaf.core.factory;

import java.util.concurrent.ConcurrentHashMap;

import com.bamboo.leaf.core.generator.SegmentGenerator;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public abstract class AbstractSegmentGeneratorFactory implements SegmentGeneratorFactory {

    private static ConcurrentHashMap<String, SegmentGenerator> generatorMap = new ConcurrentHashMap<>();

    @Override
    public SegmentGenerator getSegmentGenerator(String namespace) {
        if (generatorMap.containsKey(namespace)) {
            return generatorMap.get(namespace);
        }
        synchronized (this) {
            if (generatorMap.containsKey(namespace)) {
                return generatorMap.get(namespace);
            }
            SegmentGenerator generator = createSegmentGenerator(namespace);
            generatorMap.put(namespace, generator);
            return generator;
        }
    }

    /**
     * 根据namespace创建id生成器
     *
     * @param namespace
     * @return
     */
    protected abstract SegmentGenerator createSegmentGenerator(String namespace);
}
