package com.bamboo.leaf.client;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.utils.NumberUtils;
import com.bamboo.leaf.client.utils.PropertiesLoader;
import com.xiaoju.uemc.tinyid.client.service.impl.HttpSegmentIdServiceImpl;

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
public class GeneratorFactoryClient {

    private static final Logger logger = Logger.getLogger(GeneratorFactoryClient.class.getName());

    private static GeneratorFactoryClient generatorFactoryClient;

    private static final String DEFAULT_PROP = "bamboo-leaf-client.properties";

    private static final int DEFAULT_TIME_OUT = 5000;

    private static String serverUrl = "http://{0}/bamboo-leaf/segment/nextSegmentRange?token={1}&namespace=";

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

        if (leafToken == null || "".equals(leafToken.trim())
                || leafServer == null || "".equals(leafServer.trim())) {
            throw new IllegalArgumentException("cannot find bamboo-leaf.token and bamboo-leaf.server config in:" + location);
        }

        ClientConfig clientConfig = ClientConfig.getInstance();
        clientConfig.setLeafServer(leafServer);
        clientConfig.setLeafToken(leafToken);
        clientConfig.setReadTimeout(NumberUtils.toInt(readTimeout, DEFAULT_TIME_OUT));
        clientConfig.setConnectTimeout(NumberUtils.toInt(connectTimeout, DEFAULT_TIME_OUT));

        String[] tinyIdServers = leafServer.split(",");
        List<String> serverList = new ArrayList<>(tinyIdServers.length);
        for (String server : tinyIdServers) {
            String url = MessageFormat.format(serverUrl, server, leafToken);
            serverList.add(url);
        }
        logger.info("init tinyId client success url info:" + serverList);
        clientConfig.setServerList(serverList);
    }

    @Override
    protected IdGenerator createIdGenerator(String namespace) {
        return new CachedIdGenerator(namespace, new HttpSegmentIdServiceImpl());
    }

}
