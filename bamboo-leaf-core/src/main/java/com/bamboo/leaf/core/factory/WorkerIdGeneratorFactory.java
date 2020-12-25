package com.bamboo.leaf.core.factory;

import com.bamboo.leaf.core.generator.SnowflakeGenerator;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/25 下午10:03
 */
public interface WorkerIdGeneratorFactory {

    /**
     * 根据namespace创建id生成器
     *
     * @param namespace
     * @param hostIp
     * @return
     */
    Integer getWorkerId(String namespace, String hostIp);

    /**
     * 根据namespace创建Snowflake生成器
     *
     * @param namespace
     * @param workerId
     * @return
     */
    SnowflakeGenerator getSnowflakeGenerator(String namespace, int workerId);
}
