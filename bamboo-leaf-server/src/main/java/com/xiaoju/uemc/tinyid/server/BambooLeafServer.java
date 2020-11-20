package com.xiaoju.uemc.tinyid.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
@EnableAsync
@SpringBootApplication
@EnableScheduling
public class BambooLeafServer {

    public static void main(String[] args) {
        SpringApplication.run(BambooLeafServer.class, args);
    }
}
