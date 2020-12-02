package com.bamboo.leaf.client.service;

import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: Local模式的实现类
 * @Author: Zhuzhi
 * @Date: 2020/12/2 下午11:04
 */
public class LocalSegmentServiceImpl implements SegmentService {

    private static final Logger logger = LoggerFactory.getLogger(LocalSegmentServiceImpl.class);

    @Override
    public SegmentRange getNextSegmentRange(String namespace) {

        return null;
    }
}
