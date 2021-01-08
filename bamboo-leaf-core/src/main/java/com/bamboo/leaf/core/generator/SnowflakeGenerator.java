package com.bamboo.leaf.core.generator;

/**
 * @description: 雪花算法通用接口
 * @Author: Zhuzhi
 * @Date: 2020/12/22 下午7:23
 */
public interface SnowflakeGenerator {
    /**
     * Get a unique ID
     *
     * @return
     */
    Long nextId();


    /**
     * Parse the snowId into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param snowId
     * @return Parsed info
     */
    String parseSnowId(long snowId);
}
