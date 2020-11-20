package com.bamboo.leaf.common.factory;

import java.util.concurrent.ConcurrentHashMap;

import com.bamboo.leaf.common.generator.SegmentGenerator;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public abstract class AbstractIdGeneratorFactory implements SegmentGeneratorFactory {

    private static ConcurrentHashMap<String, SegmentGenerator> generators = new ConcurrentHashMap<>();

    @Override
    public SegmentGenerator getSegmentGenerator(String namespace) {
        if (generators.containsKey(namespace)) {
            return generators.get(namespace);
        }
        synchronized (this) {
            if (generators.containsKey(namespace)) {
                return generators.get(namespace);
            }
            SegmentGenerator generator = createSegmentGenerator(namespace);
            generators.put(namespace, generator);
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
