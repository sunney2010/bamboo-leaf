package com.bamboo.leaf.client.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @description: Properties工具类
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
public class PropertiesLoader {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    private PropertiesLoader() {

    }

    public static Properties loadProperties(String location) {
        logger.info("Loading properties file from path:{}", location);
        Properties props = new Properties();
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(PropertiesLoader.class.getClassLoader().getResourceAsStream(location), "UTF-8");
            props.load(in);
        } catch (Exception e) {
            logger.error("props.load is error,msg:", e);
            throw new IllegalStateException(e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("error close inputStream", e);
                }
            }
        }
        return props;

    }

}
