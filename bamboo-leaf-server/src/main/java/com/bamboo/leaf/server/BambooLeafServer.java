package com.bamboo.leaf.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zhuzhi
 * @date 2020/11/26
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BambooLeafServer {

    public static void main(String[] args) {
        SpringApplication.run(BambooLeafServer.class, args);
    }
}