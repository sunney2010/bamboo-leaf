package com.bamboo.leaf.demo.mybatis.controller;

import com.bamboo.leaf.client.service.BambooLeafSnowflakeClient;
import com.bamboo.leaf.core.util.PNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/28 下午11:05
 */
@RestController
public class SnowflakeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SnowflakeController.class);

    @Autowired
    BambooLeafSnowflakeClient bambooLeafSnowflakeClient;

    @RequestMapping("/snowflake/snowflakeId")
    public ModelMap snowflakeId(String namespace) {
        logger.info("snowflakeId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long snowflakeId = bambooLeafSnowflakeClient.snowflakeId(namespace);
            super.insertDemo(snowflakeId + "", namespace, "snowflakeId");

            result.put("snowflakeId", snowflakeId + "");
            result.put("currentTime", LocalDateTime.now());
            logger.info("snowflakeId is success,namespace:{},snowflakeId:{}", namespace, snowflakeId);
        } catch (Exception e) {
            logger.error("snowflakeId error", e);
        }
        return result;
    }

    @RequestMapping("/snowflake/parsSnowflakeId")
    public ModelMap parsSnowflakeId(String namespace, Long snowflakeId) {
        logger.info("snowflakeId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            String res = bambooLeafSnowflakeClient.parsSnowflakeId(namespace, snowflakeId);
            super.insertDemo(res + "", namespace, "parsSnowflakeId");

            result.put("result", res);
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
            super.insertDemo(snowflakeId16 + "", namespace, "snowflakeId16");

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
            super.insertDemo(snowflakeId20 + "", namespace, "snowflakeId20");

            result.put("snowflakeId20", snowflakeId20);
            result.put("currentTime", LocalDateTime.now());
            logger.info("snowflakeId20 is success,namespace:{},snowflakeId:{}", namespace, snowflakeId20);
        } catch (Exception e) {
            logger.error("snowflakeId20 error", e);
        }
        return result;
    }

    @RequestMapping("/snowflake/queryWorkerId")
    public ModelMap queryWorkerId(String namespace) {
        logger.info("queryWorkerId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            String hospIp = PNetUtils.getLocalHost();
            Integer workerId = bambooLeafSnowflakeClient.queryWorkerId(namespace, hospIp);
            result.put("workerId", workerId + "");
            result.put("currentIp", hospIp + "");
            result.put("currentTime", LocalDateTime.now());
            logger.info("queryWorkerId is success,namespace:{},queryWorkerId:{}", namespace, workerId);
        } catch (Exception e) {
            logger.error("queryWorkerId error", e);
        }
        return result;
    }
}
