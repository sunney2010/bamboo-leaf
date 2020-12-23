package com.bamboo.leaf.core.factory;

import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.generator.SnowflakeGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public abstract class AbstractSegmentGeneratorFactory implements SegmentGeneratorFactory {

    private static ConcurrentHashMap<String, SegmentGenerator> generatorMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, SnowflakeGenerator> snowflakeMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Integer> workerIdMap = new ConcurrentHashMap<>();

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

    @Override
    public SnowflakeGenerator getSnowflakeGenerator(String namespace,int workerId) {
        if (snowflakeMap.containsKey(namespace)) {
            return snowflakeMap.get(namespace);
        }
        synchronized (this) {
            if (snowflakeMap.containsKey(namespace)) {
                return snowflakeMap.get(namespace);
            }
            SnowflakeGenerator generator = createSnowflakeGenerator(workerId);
            snowflakeMap.put(namespace, generator);
            return generator;
        }
    }

    @Override
    public Integer getWorkerId(String namespace, String hostIp) {
        String key = namespace + "-" + hostIp;
        if (workerIdMap.containsKey(key)) {
            return workerIdMap.get(key);
        }
        synchronized (this) {
            if (workerIdMap.containsKey(key)) {
                return workerIdMap.get(key);
            }
            Integer workerId = createWorkerId(namespace, hostIp);
            workerIdMap.put(key, workerId);
            return workerId;
        }
    }

    /**
     * 根据namespace创建id生成器
     *
     * @param namespace
     * @return
     */
    protected abstract SegmentGenerator createSegmentGenerator(String namespace);

    /**
     * 根据namespace创建id生成器
     *
     * @param workerId
     * @return
     */
    protected abstract SnowflakeGenerator createSnowflakeGenerator(int workerId);

    /**
     * 根据namespace创建id生成器
     *
     * @param namespace
     * @param hostIp
     * @return
     */
    protected abstract Integer createWorkerId(String namespace, String hostIp);
}
