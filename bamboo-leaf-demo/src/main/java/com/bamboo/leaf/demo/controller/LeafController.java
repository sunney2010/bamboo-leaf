package com.bamboo.leaf.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichunming
 * @date 2020/11/19
 */
@RestController
public class LeafController {

    private static final Logger logger = LoggerFactory.getLogger(LeafController.class);


    @RequestMapping("/hello")
    public String hello() {
        logger.info("hello success");
        return "success";
    }

}
