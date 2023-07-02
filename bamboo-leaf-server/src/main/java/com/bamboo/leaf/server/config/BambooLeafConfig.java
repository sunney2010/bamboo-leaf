package com.bamboo.leaf.server.config;

import com.bamboo.leaf.core.dao.SegmentDAO;
import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.dao.impl.SegmentDAOImpl;
import com.bamboo.leaf.core.dao.impl.WorkerIdDAOImpl;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.core.service.impl.SegmentServiceImpl;
import com.bamboo.leaf.core.service.impl.WorkerIdServiceImpl;
import org.springframework.context.annotation.Bean;

/**
 * @description: 自动配置
 * @Author: Zhuzhi
 * @Date: 2020/12/3 下午8:15
 */

public class BambooLeafConfig {
    @Bean
    public SegmentDAO segmentDAO() {
        SegmentDAO segmentDAO = new SegmentDAOImpl();
        return segmentDAO;
    }

    @Bean
    public SegmentService segmentService() {
        SegmentService segmentService = new SegmentServiceImpl();
        return segmentService;
    }

    @Bean
    public WorkerIdDAO workerIdDAO() {
        WorkerIdDAO workerIdDAO = new WorkerIdDAOImpl();
        return workerIdDAO;
    }

    @Bean
    public WorkerIdService workerIdService() {
        WorkerIdService workerIdService = new WorkerIdServiceImpl();
        return workerIdService;
    }
}
