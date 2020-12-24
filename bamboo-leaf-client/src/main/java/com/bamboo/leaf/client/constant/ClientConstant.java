package com.bamboo.leaf.client.constant;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/25 上午12:03
 */
public class ClientConstant {

    public static final String DEFAULT_PROPERTIES = "bamboo-leaf-client.properties";
    public static final int DEFAULT_TIME_OUT = 5000;
    public static final String segmentServerUrl = "http://{0}/bamboo-leaf/segment/nextSegmentRange?token={1}&namespace=";
    public static final String snowServerUrl = "http://{0}/bamboo-leaf/snowflake/queryWorkerId?token={1}&hostIp={2}&namespace=";

}
