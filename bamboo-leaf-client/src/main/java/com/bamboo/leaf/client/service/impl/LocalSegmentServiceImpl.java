package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ApplicationContextProvider;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: Local模式的实现类
 * @Author: Zhuzhi
 * @Date: 2020/12/2 下午11:04
 */
@Component("localSegmentService")
public class LocalSegmentServiceImpl implements SegmentService {

    private static final Logger logger = LoggerFactory.getLogger(LocalSegmentServiceImpl.class);

    @Resource
    SegmentService segmentService;

    public LocalSegmentServiceImpl() {
        logger.info("load segmentService");
        segmentService = ApplicationContextProvider.getBean("segmentService", SegmentService.class);
    }

    @Override
    public SegmentRange getNextSegmentRange(String namespace) {
        logger.info("getNextSegmentRange namespace:{}", namespace);
        return segmentService.getNextSegmentRange(namespace);
    }
}
