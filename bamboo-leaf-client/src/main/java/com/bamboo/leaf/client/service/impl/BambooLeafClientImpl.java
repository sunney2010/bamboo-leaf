package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafClient;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.factory.AbstractSegmentGeneratorFactory;
import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.generator.SnowflakeGenerator;
import com.bamboo.leaf.core.generator.impl.CachedSegmentGenerator;
import com.bamboo.leaf.core.generator.impl.DefaultSnowflakeGenerator;
import com.bamboo.leaf.core.generator.impl.DefaultWorkerIdGenerator;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.core.util.PNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:04
 */
@Service
public class BambooLeafClientImpl extends AbstractSegmentGeneratorFactory implements BambooLeafClient, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BambooLeafClientImpl.class);

    private ApplicationContext applicationContext;

    @Resource(name = "segmentService")
    SegmentService localSegmentService;

    @Resource(name = "workerIdService")
    WorkerIdService localWorkerIdService;

    @Override
    public Long segmentId(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace is null");
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace);
        return generator.nextId();
    }

    @Override
    public long snowId(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace is null");
        }
        String hospIp = PNetUtils.getLocalHost();
        // get workerId
        Integer workerId = this.getWorkerId(namespace, hospIp);
        SnowflakeGenerator SnowflakeGenerator = this.getSnowflakeGenerator(namespace, workerId);

        return SnowflakeGenerator.nextId();
    }

    @Override
    protected SegmentGenerator createSegmentGenerator(String namespace) {
        SegmentGenerator segmentGenerator = null;
        //获取当前的配置的模式
        String mode = ClientConfig.getInstance().getMode();
        if (null == mode || mode.trim().length() == 0) {
            throw new BambooLeafException("bamboo.leaf.client.mode is not null");
        }
        //判断配置的模式
        if (mode.equalsIgnoreCase(ModeEnum.Remote.name())) {
            segmentGenerator = new CachedSegmentGenerator(namespace, new RemoteSegmentServiceImpl());
        } else if (mode.equalsIgnoreCase(ModeEnum.Local.name())) {
            segmentGenerator = new CachedSegmentGenerator(namespace, localSegmentService);
        }
        return segmentGenerator;
    }

    @Override
    protected SnowflakeGenerator createSnowflakeGenerator(int workerId) {
        return new DefaultSnowflakeGenerator(workerId);
    }

    @Override
    protected Integer createWorkerId(String namespace, String hostIp) {
        Integer workerId = null;
        //获取当前的配置的模式
        String mode = ClientConfig.getInstance().getMode();
        if (null == mode || mode.trim().length() == 0) {
            throw new BambooLeafException("bamboo.leaf.client.mode is not null");
        }
        //判断配置的模式
        if (mode.equalsIgnoreCase(ModeEnum.Remote.name())) {
            workerId = new RemoteWorkerIdServiceImpl().getWorkerId(namespace, hostIp);
        } else if (mode.equalsIgnoreCase(ModeEnum.Local.name())) {
            workerId = new DefaultWorkerIdGenerator(localWorkerIdService, namespace, hostIp).getWorkerId();
        }
        return workerId;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
