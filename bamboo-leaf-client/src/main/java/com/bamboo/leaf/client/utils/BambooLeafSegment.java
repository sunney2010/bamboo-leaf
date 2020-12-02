package com.bamboo.leaf.client.utils;

import com.bamboo.leaf.client.GeneratorFactoryClient;
import com.bamboo.leaf.core.generator.SegmentGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class BambooLeafSegment {
    private static GeneratorFactoryClient client = GeneratorFactoryClient.getInstance(null);

    private BambooLeafSegment() {

    }

    public static Long nextId(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace is null");
        }
        SegmentGenerator idGenerator = client.getSegmentGenerator(namespace);
        return idGenerator.nextId();
    }

    public static List<Long> nextId(String namespace, Integer batchSize) {
        if (batchSize == null) {
            Long id = nextId(namespace);
            List<Long> list = new ArrayList<>();
            list.add(id);
            return list;
        }
        SegmentGenerator idGenerator = client.getSegmentGenerator(namespace);
        return idGenerator.nextId(batchSize);
    }

}
