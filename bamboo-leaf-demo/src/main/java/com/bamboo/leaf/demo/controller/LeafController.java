package com.bamboo.leaf.demo.controller;

import com.bamboo.leaf.client.BambooLeafSegment;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.entity.SegmentRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * @author lichunming
 * @date 2020/11/19
 */
@RestController
public class LeafController {

    private static final Logger logger = LoggerFactory.getLogger(LeafController.class);

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
            long leafVal = BambooLeafSegment.nextId(namespace);
            result.put("leafVal", leafVal);
            result.put("currentTime", LocalDateTime.now());
            logger.info("nextSegment is success,namespace:{},leafVal:{}", namespace, leafVal);
        } catch (Exception e) {
            logger.error("nextSegment error", e);
        }
        return result;
    }

}
