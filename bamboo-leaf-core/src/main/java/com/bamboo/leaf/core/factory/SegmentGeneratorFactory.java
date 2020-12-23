package com.bamboo.leaf.core.factory;

import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.generator.SnowflakeGenerator;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface SegmentGeneratorFactory {
    /**
     * 根据namespace创建id生成器
     *
     * @param namespace
     * @return
     */
    SegmentGenerator getSegmentGenerator(String namespace);

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
     * @return
     */
    SnowflakeGenerator getSnowflakeGenerator(String namespace,int workerId);
}
