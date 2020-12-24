package com.bamboo.leaf.demo.controller;

import com.bamboo.leaf.client.service.BambooLeafClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author lichunming
 * @date 2020/11/19
 */
@RestController
public class LeafController {

    private static final Logger logger = LoggerFactory.getLogger(LeafController.class);
    @Resource
    BambooLeafClient bambooLeafClient;

    @RequestMapping("/segment/hello")
    public ModelMap hello() {
        logger.info("hello success");
        ModelMap result = new ModelMap();
        result.put("result", "success");
        result.put("currentTime", LocalDateTime.now());
        return result;
    }

    @RequestMapping("/segment/nextSegment")
    public ModelMap nextSegment(String namespace) {
        logger.info("nextSegment parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long leafVal = bambooLeafClient.segmentId(namespace);
            result.put("leafVal", leafVal);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSegment is success,namespace:{},leafVal:{}", namespace, leafVal);
        } catch (Exception e) {
            logger.error("nextSegment error", e);
        }
        return result;
    }
    @RequestMapping("/snowflake/nextSnowId")
    public ModelMap nextSnowId(String namespace) {
        logger.info("snowflake parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long snowId = bambooLeafClient.snowId(namespace);
            result.put("snowId", snowId);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSegment is success,namespace:{},snowId:{}", namespace, snowId);
        } catch (Exception e) {
            logger.error("nextSnowId error", e);
        }
        return result;
    }

}
