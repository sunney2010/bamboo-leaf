package com.bamboo.leaf.demo.mybatis.controller;

import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
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
 * @Date: 2020/12/28 下午10:56
 */
@RestController
public class SegmentController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SegmentController.class);
    @Autowired
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
            super.insertDemo(dateVal + "", namespace, "dateSegmentId");

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
            super.insertDemo(timeVal + "", namespace, "timeSegmentId");

            result.put("timeVal", timeVal + "");
            result.put("currentTime", LocalDateTime.now());
            logger.info("timeSegmentId is success,namespace:{}", namespace);
            logger.info("timeVal:{}", timeVal);

        } catch (Exception e) {
            logger.error("timeSegmentId error", e);
        }
        return result;
    }

}
