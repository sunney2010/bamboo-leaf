package com.bamboo.leaf.demo.mybatis.configure;

import javax.annotation.PostConstruct;

public class DruidConfig {
    @PostConstruct
    public void setProperties() {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }
}
