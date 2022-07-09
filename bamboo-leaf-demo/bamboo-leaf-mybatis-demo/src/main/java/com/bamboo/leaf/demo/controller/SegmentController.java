package com.bamboo.leaf.demo.controller;

import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
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
 * @Date: 2020/12/28 下午10:56
 */
@RestController
public class SegmentController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(SegmentController.class);
    @Resource
    BambooLeafSegmentClient bambooLeafSegmentClient;


    @RequestMapping("/segment/segmentId")
    public ModelMap segmentId(String namespace) {
        logger.info("segmentId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long leafVal = bambooLeafSegmentClient.segmentId(namespace);
            super.insertDemo(leafVal + "", namespace, "segmentId");
            
            result.put("leafVal", leafVal);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSegment is success,namespace:{}", namespace);
            logger.info("leafVal:{}", leafVal);

        } catch (Exception e) {
            logger.error("segmentId error", e);
        }
        return result;
    }

    @RequestMapping("/segment/dateSegmentId")
    public ModelMap dateSegmentId(String namespace) {
        logger.info("dateSegmentId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long dateVal = bambooLeafSegmentClient.dateSegmentId(namespace);
            super.insertDemo(dateVal+"",namespace,"dateSegmentId");

            result.put("dateVal", dateVal + "");
            result.put("currentTime", LocalDateTime.now());
            logger.info("dateSegmentId is success,namespace:{}", namespace);
            logger.info("dateVal:{}", dateVal);

        } catch (Exception e) {
            logger.error("nextSegment error", e);
        }
        return result;
    }

    @RequestMapping("/segment/timeSegmentId")
    public ModelMap timeSegmentId(String namespace) {
        logger.info("timeSegmentId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            long timeVal = bambooLeafSegmentClient.timeSegmentId(namespace);
            super.insertDemo(timeVal+"",namespace,"timeSegmentId");

            result.put("timeVal", timeVal + "");
            result.put("currentTime", LocalDateTime.now());
            logger.info("timeSegmentId is success,namespace:{}", namespace);
            logger.info("timeVal:{}", timeVal);

        } catch (Exception e) {
            logger.error("timeSegmentId error", e);
        }
        return result;
    }

    @RequestMapping("/segment/timeSegmentIdPrefix")
    public ModelMap timeSegmentIdPrefix(String namespace, String prefix) {
        logger.info("nextSegment parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            String timePixedVal = bambooLeafSegmentClient.timeSegmentId(namespace, prefix);
            super.insertDemo(timePixedVal+"",namespace,"timeSegmentIdPrefix");

            result.put("timePixedVal", timePixedVal);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSegment is success,namespace:{}", namespace);
            logger.info("timePixedVal:{}", timePixedVal);

        } catch (Exception e) {
            logger.error("nextSegment error", e);
        }
        return result;
    }

    @RequestMapping("/segment/dateSegmentIdPrefix")
    public ModelMap dateSegmentIdPrefix(String namespace, String prefix) {
        logger.info("nextSegment parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            String datePixedVal = bambooLeafSegmentClient.dateSegmentId(namespace, prefix);

            super.insertDemo(datePixedVal + "", namespace, "dateSegmentIdPrefix");

            result.put("datePixedVal", datePixedVal);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSegment is success,namespace:{}", namespace);
            logger.info("datePixedVal:{}", datePixedVal);
        } catch (Exception e) {
            logger.error("nextSegment error", e);
        }
        return result;
    }
}
