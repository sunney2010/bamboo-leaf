package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.config.LeafClientProperties;
import com.bamboo.leaf.client.constant.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafClient;
import com.bamboo.leaf.client.utils.NumberUtils;
import com.bamboo.leaf.client.utils.PropertiesLoader;
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:04
 */
@Service
public class BambooLeafClientImpl extends AbstractSegmentGeneratorFactory implements BambooLeafClient, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BambooLeafClientImpl.class);

    private ApplicationContext applicationContext;
    private static final String DEFAULT_PROP = "bamboo-leaf-client.properties";
    private static final int DEFAULT_TIME_OUT = 5000;
    private static String serverUrl = "http://{0}/bamboo-leaf/segment/nextSegmentRange?token={1}&namespace=";


    @Resource(name = "segmentService")
    SegmentService localSegmentService;


    @PostConstruct
    public void init() {
        if (localSegmentService == null) {
            applicationContext.getBean("segmentService", SegmentService.class);
        }
        Properties properties = PropertiesLoader.loadProperties(DEFAULT_PROP);
        String leafToken = properties.getProperty("bamboo-leaf.token");
        String leafServer = properties.getProperty("bamboo-leaf.server");
        String readTimeout = properties.getProperty("bamboo-leaf.readTimeout");
        String connectTimeout = properties.getProperty("bamboo-leaf.connectTimeout");
        String mode = properties.getProperty("bamboo-leaf.mode");

        if (leafToken == null || "".equals(leafToken.trim())
                || leafServer == null || "".equals(leafServer.trim())) {
            throw new IllegalArgumentException("cannot find bamboo-leaf.token and bamboo-leaf.server config in:" + DEFAULT_PROP);
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        clientConfig.setLeafServer(leafServer);
        clientConfig.setLeafToken(leafToken);
        clientConfig.setMode(mode);
        clientConfig.setReadTimeout(NumberUtils.toInt(readTimeout, DEFAULT_TIME_OUT));
        clientConfig.setConnectTimeout(NumberUtils.toInt(connectTimeout, DEFAULT_TIME_OUT));

        String[] leafServers = leafServer.split(",");
        List<String> serverList = new ArrayList<>(leafServers.length);
        for (String server : leafServers) {
            String url = MessageFormat.format(serverUrl, server, leafToken);
            serverList.add(url);
        }
        logger.info("bamboo-leaf client init success url info:" + serverList);
        clientConfig.setServerList(serverList);
    }

    @Override
    public Long nextId(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace is null");
        }
        SegmentGenerator generator = this.getSegmentGenerator(namespace);
        return generator.nextId();
    }

    @Override
    protected SegmentGenerator createSegmentGenerator(String namespace) {
        SegmentGenerator segmentGenerator = null;
        //获取当前的配置的模式
        String mode = ClientConfig.getInstance().getMode();
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
