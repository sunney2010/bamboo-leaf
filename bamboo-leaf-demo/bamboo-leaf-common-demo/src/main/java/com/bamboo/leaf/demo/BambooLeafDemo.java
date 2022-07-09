package com.bamboo.leaf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhuzhi
 * @date 2020/11/26
 */
@SpringBootApplication
@EnableScheduling
public class BambooLeafDemo {

    public static void main(String[] args) {
        SpringApplication.run(BambooLeafDemo.class, args);
    }
}