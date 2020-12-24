package com.bamboo.leaf.client;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ModeEnum;
import com.bamboo.leaf.client.service.impl.LocalSegmentServiceImpl;
import com.bamboo.leaf.client.service.impl.RemoteSegmentServiceImpl;
import com.bamboo.leaf.client.utils.NumberUtils;
import com.bamboo.leaf.client.utils.PropertiesLoader;
import com.bamboo.leaf.core.factory.AbstractSegmentGeneratorFactory;
import com.bamboo.leaf.core.generator.SegmentGenerator;
import com.bamboo.leaf.core.generator.SnowflakeGenerator;
import com.bamboo.leaf.core.generator.impl.CachedSegmentGenerator;
import com.bamboo.leaf.core.service.SegmentService;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午11:55
 */
public class GeneratorFactoryClient extends AbstractSegmentGeneratorFactory {

    private static final Logger logger = Logger.getLogger(GeneratorFactoryClient.class.getName());

    private static GeneratorFactoryClient generatorFactoryClient;

    private static final String DEFAULT_PROP = "bamboo-leaf-client.properties";

    private static final int DEFAULT_TIME_OUT = 5000;

    private static String serverUrl = "http://{0}/bamboo-leaf/segment/nextSegmentRange?token={1}&namespace=";

    @Resource(name="remoteSegmentService")
    static SegmentService remoteSegmentService;

    @Resource(name="localSegmentService")
    static SegmentService localSegmentService;

    private GeneratorFactoryClient() {

    }

    public static GeneratorFactoryClient getInstance(String location) {
        if (generatorFactoryClient == null) {
            synchronized (GeneratorFactoryClient.class) {
                if (generatorFactoryClient == null) {
                    if (location == null || "".equals(location)) {
                        init(DEFAULT_PROP);
                    } else {
                        init(location);
                    }
                }
            }
        }
        return generatorFactoryClient;
    }

    private static void init(String location) {
        generatorFactoryClient = new GeneratorFactoryClient();
        Properties properties = PropertiesLoader.loadProperties(location);
        String leafToken = properties.getProperty("bamboo-leaf.token");
        String leafServer = properties.getProperty("bamboo-leaf.server");
        String readTimeout = properties.getProperty("bamboo-leaf.readTimeout");
        String connectTimeout = properties.getProperty("bamboo-leaf.connectTimeout");
        String mode = properties.getProperty("bamboo-leaf.mode");

        if (leafToken == null || "".equals(leafToken.trim())
                || leafServer == null || "".equals(leafServer.trim())) {
            throw new IllegalArgumentException("cannot find bamboo-leaf.token and bamboo-leaf.server config in:" + location);
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
        clientConfig.setSegmentServerList(serverList);
    }


    @Override
    protected SegmentGenerator createSegmentGenerator(String namespace) {
        SegmentGenerator segmentGenerator = null;
        //获取当前的配置的模式
        String mode=ClientConfig.getInstance().getMode();
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
        return null;
    }

    @Override
    protected Integer createWorkerId(String namespace, String hostIp) {
        return null;
    }
}
