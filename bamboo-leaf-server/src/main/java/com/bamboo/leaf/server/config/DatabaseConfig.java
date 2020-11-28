package com.bamboo.leaf.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/28 下午2:04
 */
@Configuration
public class DatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        // 这里要将配置的 DruidDataSource 数据源注入 JdbcTemplate 中，不然默认注入 Spring Boot 自带的 HikariDatasource。
        return new JdbcTemplate(dataSource());
    }
}