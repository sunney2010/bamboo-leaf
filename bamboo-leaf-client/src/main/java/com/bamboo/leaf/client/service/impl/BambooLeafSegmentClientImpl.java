package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.factory.AbstractSegmentGeneratorFactory;
import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.generator.impl.CachedSegmentGenerator;
import com.bamboo.leaf.core.service.SegmentService;
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
public class BambooLeafSegmentClientImpl extends AbstractSegmentGeneratorFactory implements BambooLeafSegmentClient, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BambooLeafSegmentClientImpl.class);

    private ApplicationContext applicationContext;

    @Resource(name = "segmentService")
    SegmentService localSegmentService;


    @Override
    public Long segmentId(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace is null");
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace);
        return generator.nextId();
    }

    @Override
    public Long dateSegmentId(String namespace) {
        return null;
    }

    @Override
    public Long timeSegmentId(String namespace) {
        return null;
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
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
