package com.bamboo.leaf.client.service;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/25 下午6:30
 */
public interface BambooLeafSnowflakeClient {
    /**
     * 获取snowflake算法序列
     *
     * @param namespace 命名空间
     * @return
     */
    public long snowflakeId(String namespace);

    /**
     * 获取snowflake算法序列
     *
     * @param namespace 命名空间
     * @return
     */
    public String snowflakeId16(String namespace);

    /**
     * 获取snowflake算法序列
     *
     * @param namespace 命名空间
     * @return
     */
    public String snowflakeId20(String namespace);

    /**
     * Parse the snowId into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param namespace
     * @param snowflakeId
     * @return Parsed info
     */
    public String parsSnowflakeId(String namespace, long snowflakeId);

}
