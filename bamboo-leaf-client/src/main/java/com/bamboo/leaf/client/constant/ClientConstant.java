package com.bamboo.leaf.client.constant;

/**
 * @description: 客户端常量
 * @Author: Zhuzhi
 * @Date: 2020/12/25 上午12:03
 */
public class ClientConstant {

    /**
     * 默认配置文件
     */
    public static final String DEFAULT_PROPERTIES = "bamboo-leaf-client.properties";
    /**
     * 默认超时间
     */
    public static final int DEFAULT_TIME_OUT = 5000;
    /**
     * Segment算法URL
     */
    public static final String segmentServerUrl = "http://{0}/bamboo-leaf/segment/nextSegmentRange?token={1}&maxValue={2}&namespace=";
    /**
     * Snowflake算法URL
     */
    public static final String snowflakeServerUrl = "http://{0}/bamboo-leaf/snowflake/queryWorkerId?token={1}&hostIp={2}&namespace=";

}
