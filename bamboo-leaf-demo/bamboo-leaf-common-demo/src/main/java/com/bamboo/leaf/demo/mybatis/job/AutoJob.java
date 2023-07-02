package com.bamboo.leaf.demo.mybatis.job;

import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2021/7/31 下午3:04
 */
@Configuration
@EnableScheduling
public class AutoJob {

    private static final Logger logger = LoggerFactory.getLogger(AutoJob.class);
    /**
     * 线程数
     */
    public static final int THREAD_POOL_SIZE = 10;


    /**
     * 使用 ThreadFactoryBuilder 创建自定义线程名称的 ThreadFactory
     */
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("bamboo-pool-%d").build();

    ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_POOL_SIZE,
            THREAD_POOL_SIZE,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024),
            namedThreadFactory,
            new ThreadPoolExecutor.AbortPolicy());
    private static int AUTO_MAX_TIME = 1000;


    @Autowired
    BambooLeafSegmentClient bambooLeafSegmentClient;

    //@Scheduled(fixedRate = 1000, initialDelay = 1000)
    public void AutoJobTest() {
        AutoRun();
    }

    private void AutoRun() {
        for (int i = 0; i < AUTO_MAX_TIME; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    SegmentRun();
                }
            });
        }
    }

    private void SegmentRun() {
        Long dateSegmentId = bambooLeafSegmentClient.dateSegmentId("Auto-DateSegmentId");
        Long timeSegmentId = bambooLeafSegmentClient.timeSegmentId("Auto-TimeSegmentId");
        Long segmentId = bambooLeafSegmentClient.segmentId("Auto-SegmentId");
        String autoDateSegmentId = bambooLeafSegmentClient.autoDateSegmentId("autoDateSegmentId");
        logger.info("dateSegmentId:{}", dateSegmentId);
        logger.info("timeSegmentId:{}", timeSegmentId);
        logger.info("segmentId:{}", segmentId);
        logger.info("autoDateSegmentId:{}", autoDateSegmentId);
    }
}
