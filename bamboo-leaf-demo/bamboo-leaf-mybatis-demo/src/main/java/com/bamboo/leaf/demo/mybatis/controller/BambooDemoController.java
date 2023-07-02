package com.bamboo.leaf.demo.mybatis.controller;

import com.alibaba.fastjson.JSON;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import com.bamboo.leaf.demo.mybatis.Model.BambooDemoModel;
import com.bamboo.leaf.demo.mybatis.service.BambooLeafDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BambooDemoController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    BambooLeafDemoService bambooLeafDemoService;
    @Resource
    BambooLeafSegmentClient bambooLeafSegmentClient;
    
    @RequestMapping("/bamboo/insertBamboo")
    public ModelMap insertBamboo(String namespace) {
        logger.info("segmentId parameter: namespace:{}", namespace);
        ModelMap result = new ModelMap();
        try {
            BambooDemoModel model=new BambooDemoModel();
            //model.setId(bambooLeafSegmentClient.dateSegmentId(namespace,"P"));
            model.setNamespace(namespace);
            int val = bambooLeafDemoService.insertBambooDemo(model);
            result.put("value", JSON.toJSON(model));
            result.put("result", val);

        } catch (Exception e) {
            logger.error("insertBamboo error", e);
        }
        return result;
    }

    @RequestMapping("/bamboo/queryBambooDemo")
    public ModelMap queryBamboo(Long id) {
        logger.info("queryBamboo parameter: id:{}", id);
        ModelMap result = new ModelMap();
        try {
            BambooDemoModel model= bambooLeafDemoService.queryBambooDemoById(id);
            result.put("value", JSON.toJSON(model));

        } catch (Exception e) {
            logger.error("queryBamboo error", e);
        }
        return result;
    }
}
