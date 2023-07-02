package com.bamboo.leaf.demo.mybatis.controller;

import com.bamboo.leaf.client.constant.ParamConstant;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import com.bamboo.leaf.client.service.BambooLeafSnowflakeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lichunming
 * @date 2020/11/19
 */
@RestController
public class LeafController {

    private static final Logger logger = LoggerFactory.getLogger(LeafController.class);


    @Autowired
    BambooLeafSnowflakeClient bambooLeafSnowflakeClient;
    @Autowired
    BambooLeafSegmentClient bambooLeafSegmentClient;

    @RequestMapping("/segment/hello")
    public ModelMap hello() {
        logger.info("hello success");
        ModelMap result = new ModelMap();
        result.put("result", "success");
        result.put("currentTime", LocalDateTime.now());
        return result;
    }

    @RequestMapping("/segment")
    public ModelMap segment(String namespace) {
        logger.info("nextSegment parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long leafVal = bambooLeafSegmentClient.segmentId(namespace);
            long dateVal = bambooLeafSegmentClient.dateSegmentId(namespace);
            long timeVal = bambooLeafSegmentClient.timeSegmentId(namespace);
            Map<String,Object> paramMap=new HashMap<>();
            paramMap.put(ParamConstant.PARAM_PREFIX,"PREFIX");
            paramMap.put(ParamConstant.PARAM_INFIX,"INFIX");
            paramMap.put(ParamConstant.PARAM_SUFFIX,"SUFFIX");
            String datePixedVal = bambooLeafSegmentClient.dateSegmentId(namespace, paramMap);
            String timePixedVal = bambooLeafSegmentClient.timeSegmentId(namespace, paramMap);

            result.put("leafVal", leafVal);
            result.put("dateVal", dateVal + "");
            result.put("timeVal", timeVal + "");
            result.put("datePixedVal", datePixedVal);
            result.put("timePixedVal", timePixedVal);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSegment is success,namespace:{}", namespace);
            logger.info("leafVal:{}", leafVal);
            logger.info("dateVal:{}", dateVal);
            logger.info("timeVal:{}", timeVal);
            logger.info("datePixedVal:{}", datePixedVal);
            logger.info("timePixedVal:{}", timePixedVal);

        } catch (Exception e) {
            logger.error("nextSegment error", e);
        }
        return result;
    }

    @RequestMapping("/snowflake")
    public ModelMap snowflake(String namespace) {
        logger.info("snowflake parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long snowflakeId = bambooLeafSnowflakeClient.snowflakeId(namespace);
            String snowflakeId16 = bambooLeafSnowflakeClient.snowflakeId16(namespace);
            String snowflakeId20 = bambooLeafSnowflakeClient.snowflakeId20(namespace);
            String parsSnowflakeId = bambooLeafSnowflakeClient.parsSnowflakeId(namespace, snowflakeId);
            result.put("snowflakeId", snowflakeId + "");
            result.put("snowflakeId16", snowflakeId16);
            result.put("snowflakeId20", snowflakeId20);
            result.put("parsSnowflakeId", parsSnowflakeId);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSnowId is success,namespace:{},snowflakeId:{}", namespace, snowflakeId);
        } catch (Exception e) {
            logger.error("nextSnowId error", e);
        }
        return result;
    }
}
