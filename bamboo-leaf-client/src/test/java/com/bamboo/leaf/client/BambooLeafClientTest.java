package com.bamboo.leaf.client;

import org.junit.Test;

/**
 * @description:测试
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午11:55
 */
public class BambooLeafClientTest {

    @Test
    public void testNextId() {
        for (int i = 0; i < 100; i++) {
            Long id = BambooLeafSegment.nextId("segment-test");
            System.out.println("current id is: " + id);
        }
    }
}
