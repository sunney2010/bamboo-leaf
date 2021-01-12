package com.bamboo.leaf.client.constant;

/**
 * @description: 客户端常量
 * @Author: zhuzhi
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

    public static final String LEAF_SEGMENT_KEY = "segment";
    public static final String LEAF_SNOWFLAKE_KEY = "snowflake";


    public static final String LEAF_TOKEN = "token";
    public static final String LEAF_MAXVALUE = "maxValue";
    public static final String LEAF_NAMESPACE = "namespace";
    public static final String LEAF_HOSP_IP = "hostIp";
    public static final String LEAF_SEGMENT_PATH = "/bamboo-leaf/segment/nextSegmentRange";
    public static final String LEAF_SNOWFLAKE_PATH = "/bamboo-leaf/snowflake/queryWorkerId";

    /**
     * Segment算法URL
     */
    public static final String segmentServerUrl = "http://{0}/bamboo-leaf/segment/nextSegmentRange?token={1}&maxValue={2}&namespace=";
    /**
     * Snowflake算法URL
     */
    public static final String snowflakeServerUrl = "http://{0}/bamboo-leaf/snowflake/queryWorkerId?token={1}&hostIp={2}&namespace=";

}
