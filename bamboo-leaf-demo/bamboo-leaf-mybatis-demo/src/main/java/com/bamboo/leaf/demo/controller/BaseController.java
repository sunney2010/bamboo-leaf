package com.bamboo.leaf.demo.controller;

import com.bamboo.leaf.demo.dao.DemoDao;
import com.bamboo.leaf.demo.entity.DemoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2021/3/14 下午10:11
 */
@RestController
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected DemoDao demoDao;

    protected void insertDemo(String id, String namespace, String remark) {
        Class a=LeafController.class;
        Field[] f=a.getDeclaredFields();
        DemoDO demo = new DemoDO();
        demo.setId(id);
        demo.setNamespace(namespace);
        demo.setRemark(remark);
        int val = demoDao.insertDemo(demo);
        if (val == 1) {
            logger.info(" insert bamboo-leaf-demo success! id:{},namespace:{}", id, namespace);
        }
    }
}
