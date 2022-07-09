package com.bamboo.leaf.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhuzhi
 * @date 2020/11/26
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.bamboo.leaf.demo.mapper")
public class BambooLeafDemo {

    public static void main(String[] args) {
        SpringApplication.run(BambooLeafDemo.class, args);
    }
}