package com.bamboo.leaf.server.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.List;
import java.util.Random;

/**
 * @author lichunming
 * @date 2020/11/19
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    private List<String> dataSourceKeys;

    @Override
    protected Object determineCurrentLookupKey() {
        String currentDataSource = "";
        if (dataSourceKeys.size() == 1) {
            currentDataSource = dataSourceKeys.get(0);
        } else {
            Random random = new Random();
            currentDataSource = dataSourceKeys.get(random.nextInt(dataSourceKeys.size()));
        }
        if (logger.isInfoEnabled()) {
            logger.info("currentDataSource:{}", currentDataSource);
        }
        return currentDataSource;
    }

    public List<String> getDataSourceKeys() {
        return dataSourceKeys;
    }

    public void setDataSourceKeys(List<String> dataSourceKeys) {
        this.dataSourceKeys = dataSourceKeys;
    }
}
