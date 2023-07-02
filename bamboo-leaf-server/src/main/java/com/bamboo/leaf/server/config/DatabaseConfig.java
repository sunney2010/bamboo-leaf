package com.bamboo.leaf.server.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceMBean;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.LinkedList;

/**
 * @description: 数据源配置
 * @Author: Zhuzhi
 * @Date: 2020/11/28 下午2:04
 */
@Configuration
public class DatabaseConfig {
    @Bean
    public JdbcTemplate jdbcTemplate() {
        // 这里要将配置的 DruidDataSource 数据源注入 JdbcTemplate 中，不然默认注入 Spring Boot 自带的 HikariDatasource。
        return new JdbcTemplate(druidDataSource());
    }

    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();

        // 添加druid的监控过滤器，当前只演示监控的功能，因此只有一个过滤器，可以实现多个过滤器
        LinkedList<Filter> filtersList = new LinkedList();
        filtersList.add(filter());
        druidDataSource.setProxyFilters(filtersList);
        return druidDataSource;
    }

    @Bean
    public Filter filter() {
        StatFilter statFilter = new StatFilter();
        // SQL执行时间超过2s种的被判定为慢日志
        statFilter.setSlowSqlMillis(2000);
        //显示慢日志
        statFilter.setLogSlowSql(true);
        //合并SQL，有时，一些相同的慢日志过多影响阅读，开启合并功能
        statFilter.setMergeSql(true);
        return statFilter;
    }
}