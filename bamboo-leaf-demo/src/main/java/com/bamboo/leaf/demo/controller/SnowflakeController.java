package com.bamboo.leaf.demo.controller;

import com.bamboo.leaf.client.service.BambooLeafSnowflakeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/28 下午11:05
 */
@RestController
public class SnowflakeController {
    private static final Logger logger = LoggerFactory.getLogger(SnowflakeController.class);

    @Resource
    BambooLeafSnowflakeClient bambooLeafSnowflakeClient;

    @RequestMapping("/snowflake/snowflakeId")
    public ModelMap snowflakeId(String namespace) {
        logger.info("snowflakeId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long snowflakeId = bambooLeafSnowflakeClient.snowflakeId(namespace);
            result.put("snowflakeId", snowflakeId + "");
            result.put("currentTime", LocalDateTime.now());
            logger.info("snowflakeId is success,namespace:{},snowflakeId:{}", namespace, snowflakeId);
        } catch (Exception e) {
            logger.error("snowflakeId error", e);
        }
        return result;
    }

    @RequestMapping("/snowflake/snowflakeId16")
    public ModelMap snowflakeId16(String namespace) {
        logger.info("snowflakeId16 parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            String snowflakeId16 = bambooLeafSnowflakeClient.snowflakeId16(namespace);
            result.put("snowflakeId16", snowflakeId16);
            result.put("currentTime", LocalDateTime.now());
            logger.info("snowflakeId16 is success,namespace:{},snowflakeId:{}", namespace, snowflakeId16);
        } catch (Exception e) {
            logger.error("snowflakeId16 error", e);
        }
        return result;
    }

    @RequestMapping("/snowflake/snowflakeId20")
    public ModelMap snowflakeId20(String namespace) {
        logger.info("snowflakeId20 parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            String snowflakeId20 = bambooLeafSnowflakeClient.snowflakeId20(namespace);
            result.put("snowflakeId20", snowflakeId20);
            result.put("currentTime", LocalDateTime.now());
            logger.info("snowflakeId20 is success,namespace:{},snowflakeId:{}", namespace, snowflakeId20);
        } catch (Exception e) {
            logger.error("snowflakeId20 error", e);
        }
        return result;
    }
}
