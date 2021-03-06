package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import com.bamboo.leaf.core.constant.LeafConstant;
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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 客户端实现类
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:04
 */
@Service
public class BambooLeafSegmentClientImpl extends AbstractSegmentGeneratorFactory implements BambooLeafSegmentClient, ApplicationContextAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 前缀最大长度
     */
    private static final Integer PREFIX_MAX_LENGTH = 10;

    private ApplicationContext applicationContext;

    @Resource(name = "segmentService")
    SegmentService localSegmentService;


    @Override
    public Long segmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, 0);
        return generator.nextSegmentId();
    }

    @Override
    public Long dateSegmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            logger.error("namespace is null");
            throw new IllegalArgumentException("namespace is null");
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, LeafConstant.SEGMENT_DATE_MAXVALUE);
        String val = generator.nextSegmentIdFixed(LeafConstant.SEGMENT_DATE_MAXVALUE);
        StringBuilder id = new StringBuilder(20);
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        id.append(formatter.format(dt));
        id.append(val);
        return Long.valueOf(id.toString());
    }

    @Override
    public String dateSegmentId(String namespace, String prefix) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.dateSegmentId(namespace);
    }

    @Override
    public Long timeSegmentId(String namespace) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace, LeafConstant.SEGMENT_TIME_MAXVALUE);
        String val = generator.nextSegmentIdFixed(LeafConstant.SEGMENT_TIME_MAXVALUE);

        StringBuilder id = new StringBuilder(20);
        // 获取当前的系统时间
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        id.append(formatter.format(dt));
        id.append(val);
        return Long.valueOf(id.toString());
    }

    @Override
    public String timeSegmentId(String namespace, String prefix) {
        if (namespace == null || namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace is null");
        }
        if (prefix == null || prefix.trim().length() == 0) {
            throw new IllegalArgumentException("prefix is null");
        }
        if (prefix.trim().length() < 1 || prefix.trim().length() > PREFIX_MAX_LENGTH) {
            throw new IllegalArgumentException("prefix range no in [1,10]");
        }
        return prefix + this.timeSegmentId(namespace);
    }


    @Override
    protected SegmentGenerator createSegmentGenerator(String namespace, long maxValue) {
        SegmentGenerator segmentGenerator = null;
        //获取当前的配置的模式
        String mode = ClientConfig.getInstance().getMode();
        if (null == mode || mode.trim().length() == 0) {
            throw new BambooLeafException("bamboo.leaf.client.mode is not null");
        }
        //判断配置的模式
        if (mode.equalsIgnoreCase(ModeEnum.Remote.name())) {
            // 远程模式
            segmentGenerator = new CachedSegmentGenerator(namespace, maxValue, new RemoteSegmentServiceImpl());
        } else if (mode.equalsIgnoreCase(ModeEnum.Local.name())) {
            // 本地模式
            segmentGenerator = new CachedSegmentGenerator(namespace, maxValue, localSegmentService);
        }
        return segmentGenerator;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
