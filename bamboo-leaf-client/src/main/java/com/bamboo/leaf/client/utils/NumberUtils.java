package com.bamboo.leaf.client.utils;

/**
 * @description: Number工具类
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class NumberUtils {

    public static int toInt(String str) {
        return toInt(str, 0);
    }

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
