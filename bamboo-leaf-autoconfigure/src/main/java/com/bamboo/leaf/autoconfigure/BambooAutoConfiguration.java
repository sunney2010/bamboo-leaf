package com.bamboo.leaf.autoconfigure;

import com.bamboo.leaf.core.constant.LeafConfigure;
import com.bamboo.leaf.core.constant.TableConfigure;
import com.bamboo.leaf.core.dao.SegmentDAO;
import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.dao.impl.SegmentDAOImpl;
import com.bamboo.leaf.core.dao.impl.WorkerIdDAOImpl;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.core.service.impl.SegmentServiceImpl;
import com.bamboo.leaf.core.service.impl.WorkerIdServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
@Configuration
@EnableConfigurationProperties(LeafTableProperties.class)
public class BambooAutoConfiguration {

    private final LeafTableProperties leafTableProperties;

    private final LeafProperties leafProperties;

    public BambooAutoConfiguration(LeafTableProperties leafTableProperties,LeafProperties leafProperties) {
        this.leafTableProperties = leafTableProperties;
        this.leafProperties=leafProperties;
    }
    @Bean
    public LeafConfigure leafConfigure(){
        LeafConfigure leafConfigure=new LeafConfigure();
        leafConfigure.setLoadingPercent(leafProperties.getLoadingPercent());
        leafConfigure.setRetry(leafProperties.getRetry());
        leafConfigure.setStep(leafProperties.getStep());
        return leafConfigure;
    }
    @Bean
    public TableConfigure tableConfig() {
        TableConfigure tableConfig = new TableConfigure();

        String workerIdTableName = leafTableProperties.getWorkerIdTableName();
        if (workerIdTableName != null && (workerIdTableName = workerIdTableName.trim()).length() > 0) {
            tableConfig.setWorkIdTableName(workerIdTableName);
        }
        String segmentTableName = leafTableProperties.getSegmentTableName();
        if (segmentTableName != null && (segmentTableName = segmentTableName.trim()).length() > 0) {
            tableConfig.setSegmentTableName(segmentTableName);
        }

        String createColumnName = leafTableProperties.getCreateColumnName();
        if (createColumnName != null && (createColumnName = createColumnName.trim()).length() > 0) {
            tableConfig.setCreateColumnName(createColumnName);
        }
        String updateColumnName = leafTableProperties.getUpdateColumnName();
        if (updateColumnName != null && (updateColumnName = updateColumnName.trim()).length() > 0) {
            tableConfig.setUpdateColumnName(updateColumnName);
        }
        String remarkColumnName = leafTableProperties.getRemarkColumnName();
        if (remarkColumnName != null && (remarkColumnName = remarkColumnName.trim()).length() > 0) {
            tableConfig.setRemarkColumnName(remarkColumnName);
        }
        String deltaColumnName = leafTableProperties.getDeltaColumnName();
        if (deltaColumnName != null && (deltaColumnName = deltaColumnName.trim()).length() > 0) {
            tableConfig.setDeltaColumnName(deltaColumnName);
        }

        String namespaceColumnName = leafTableProperties.getNamespaceColumnName();
        if (namespaceColumnName != null && (namespaceColumnName = namespaceColumnName.trim()).length() > 0) {
            tableConfig.setNamespaceColumnName(namespaceColumnName);
        }
        String remainderColumnName = leafTableProperties.getRemainderColumnName();
        if (remainderColumnName != null && (remainderColumnName = remainderColumnName.trim()).length() > 0) {
            tableConfig.setRemaiderColumnName(remainderColumnName);
        }
        String retryColumnName = leafTableProperties.getRetryColumnName();
        if (retryColumnName != null && (retryColumnName = retryColumnName.trim()).length() > 0) {
            tableConfig.setRetryColumnName(retryColumnName);
        }
        String versionColumnName = leafTableProperties.getVersionColumnName();
        if (versionColumnName != null && (versionColumnName = versionColumnName.trim()).length() > 0) {
            tableConfig.setVersionColumnName(versionColumnName);
        }
        String leafValueColumnName = leafTableProperties.getLeafValueColumnName();
        if (leafValueColumnName != null && (leafValueColumnName = leafValueColumnName.trim()).length() > 0) {
            tableConfig.setLeafValueColumnName(leafValueColumnName);
        }
        String stepColumnName = leafTableProperties.getStepColumnName();
        if (stepColumnName != null && (stepColumnName = stepColumnName.trim()).length() > 0) {
            tableConfig.setStepColumnName(stepColumnName);
        }
        String hostIpColumnName = leafTableProperties.getHostIpColumnName();
        if (hostIpColumnName != null && (hostIpColumnName = hostIpColumnName.trim()).length() > 0) {
            tableConfig.setHostIpColumnName(hostIpColumnName);
        }
        String workerIdColumnName = leafTableProperties.getWorkerIdColumnName();
        if (workerIdColumnName != null && (workerIdColumnName = workerIdColumnName.trim()).length() > 0) {
            tableConfig.setWorkerIdColumnName(workerIdColumnName);
        }
        return tableConfig;

    }

    @Bean
    public SegmentDAO segmentDAO() {
        SegmentDAO segmentDAO = new SegmentDAOImpl();
        return segmentDAO;
    }

    @Bean
    public SegmentService segmentService() {
        SegmentService segmentService = new SegmentServiceImpl();
        return segmentService;
    }

    @Bean
    public WorkerIdDAO workerIdDAO() {
        WorkerIdDAO workerIdDAO = new WorkerIdDAOImpl();
        return workerIdDAO;
    }

    @Bean
    public WorkerIdService workerIdService() {
        WorkerIdService workerIdService = new WorkerIdServiceImpl();
        return workerIdService;
    }
}
