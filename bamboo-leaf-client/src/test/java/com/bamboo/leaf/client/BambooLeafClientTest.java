package com.bamboo.leaf.client;

import java.util.List;

/**
 * @description:测试
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午11:55
 */
public class BambooLeafClientTest {


    public void testNextId() {
        for (int i = 0; i < 100; i++) {
            Long id = BambooLeafSegment.nextId("segment-test");
            System.out.println("current id is: " + id);
        }
    }

    public void NextBatchId() {
        String namespace = "segment-test-batch";
        List<Long> idList = BambooLeafSegment.nextId(namespace, 100);
        System.out.println("current id is,size: " + idList.size());
    }
}
