package com.bamboo.leaf.core.factory;

import com.bamboo.leaf.core.generator.SnowflakeGenerator;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: workerId抽象工厂
 * @Author: zhuzhi
 * @Date: 2020/12/25 下午10:05
 */
public abstract class AbstractWorkerIdGeneratorFactory implements WorkerIdGeneratorFactory {
    private static ConcurrentHashMap<String, SnowflakeGenerator> snowflakeMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Integer> workerIdMap = new ConcurrentHashMap<>();

    @Override
    public SnowflakeGenerator getSnowflakeGenerator(String namespace, int workerId) {
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
    public Integer getWorkerId(String appId, String hostIp) {
        String key = appId + "-" + hostIp;
        if (workerIdMap.containsKey(key)) {
            return workerIdMap.get(key);
        }
        synchronized (this) {
            if (workerIdMap.containsKey(key)) {
                return workerIdMap.get(key);
            }
            Integer workerId = createWorkerId(appId, hostIp);
            workerIdMap.put(key, workerId);
            return workerId;
        }
    }

    /**
     * 根据namespace创建id生成器
     *
     * @param workerId
     * @return
     */
    protected abstract SnowflakeGenerator createSnowflakeGenerator(int workerId);

    /**
     * 根据appId创建id生成器
     *
     * @param appId
     * @param hostIp
     * @return
     */
    protected abstract Integer createWorkerId(String appId, String hostIp);

}
