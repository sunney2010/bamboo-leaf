package com.bamboo.leaf.client.utils;

/**
 * @description: Number工具类
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class NumberUtils {

    /**
     * String 转 int ,默认为O
     * @param str 字符串
     * @return
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * String 转 int
     * @param str 字符串
     * @param defaultValue 默认值
     * @return
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
