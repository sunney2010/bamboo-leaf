package com.bamboo.leaf.server.config;

import com.bamboo.leaf.core.dao.SegmentDAO;
import com.bamboo.leaf.core.dao.impl.SegmentDAOImpl;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.service.impl.SegmentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 自动配置
 * @Author: Zhuzhi
 * @Date: 2020/12/3 下午8:15
 */
@Configuration
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
}
