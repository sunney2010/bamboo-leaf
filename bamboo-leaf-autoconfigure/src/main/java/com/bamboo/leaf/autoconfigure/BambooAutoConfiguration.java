package com.bamboo.leaf.autoconfigure;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafClient;
import com.bamboo.leaf.client.service.impl.BambooLeafClientImpl;
import com.bamboo.leaf.client.utils.NumberUtils;
import com.bamboo.leaf.client.utils.PropertiesLoader;
import com.bamboo.leaf.core.constant.LeafConfigure;
import com.bamboo.leaf.core.constant.TableConfigure;
import com.bamboo.leaf.core.dao.SegmentDAO;
import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.dao.impl.SegmentDAOImpl;
import com.bamboo.leaf.core.dao.impl.WorkerIdDAOImpl;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.core.service.impl.SegmentServiceImpl;
import com.bamboo.leaf.core.service.impl.WorkerIdServiceImpl;
import com.bamboo.leaf.core.util.PNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/30 下午1:10
 */
@Configuration
@EnableConfigurationProperties({LeafTableProperties.class, LeafProperties.class, LeafClientProperties.class})
public class BambooAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BambooAutoConfiguration.class);
    private static final String DEFAULT_PROPERTIES = "bamboo-leaf-client.properties";
    private static final int DEFAULT_TIME_OUT = 5000;
    private static String segmentServerUrl = "http://{0}/bamboo-leaf/segment/nextSegmentRange?token={1}&namespace=";
    private static String snowServerUrl = "http://{0}/bamboo-leaf/snowflake/queryWorkerId?token={1}&hostIp={2}&namespace=";


    private final LeafTableProperties leafTableProperties;
    private final LeafProperties leafProperties;
    private final LeafClientProperties leafClientProperties;

    public BambooAutoConfiguration(LeafProperties leafProperties, LeafTableProperties leafTableProperties, LeafClientProperties leafClientProperties) {
        this.leafTableProperties = leafTableProperties;
        this.leafProperties = leafProperties;
        this.leafClientProperties = leafClientProperties;
    }

    @Bean
    public LeafConfigure leafConfigure() {
        LeafConfigure leafConfigure = new LeafConfigure();
        leafConfigure.setLoadingPercent(leafProperties.getLoadingPercent());
        leafConfigure.setRetry(leafProperties.getRetry());
        leafConfigure.setStep(leafProperties.getStep());
        return leafConfigure;
    }

    @Bean
    public TableConfigure tableConfigure() {
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
    public ClientConfig clientConfig() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        Properties properties = null;
        if (leafClientProperties == null) {
            try {
                properties = PropertiesLoader.loadProperties(DEFAULT_PROPERTIES);
            } catch (Exception e) {
                logger.warn("Please confirm whether {} exists", DEFAULT_PROPERTIES);
                throw new BambooLeafException("Client properties is not exists!");
            }
        }
        // 模式
        String mode = leafClientProperties.getMode();
        if (mode == null || (mode = mode.trim()).length() == 0) {
            mode = properties.getProperty("bamboo.leaf.client.mode");
        }
        // 判断模式
        if (mode == null || (mode = mode.trim()).length() == 0) {
            throw new BambooLeafException("bamboo.leaf.client.mode is not null!");
        }
        clientConfig.setMode(mode);
        // Remote 模式要判断leafServer，leafToken
        if (mode.equalsIgnoreCase(ModeEnum.Remote.name())) {
            // 服务地址
            String leafServer = leafClientProperties.getLeafServer();
            if (leafServer == null || (leafServer = leafServer.trim()).length() == 0) {
                leafServer = properties.getProperty("bamboo.leaf.client.leafServer");
            }
            // 判断服务地址
            if (leafServer == null || (leafServer = leafServer.trim()).length() == 0) {
                throw new BambooLeafException("mode=Remote ,bamboo.leaf.client.leafServer is not null!");
            }
            clientConfig.setLeafServer(leafServer);

            //Token
            String leafToken = leafClientProperties.getLeafServer();
            if (leafToken == null || (leafToken = leafToken.trim()).length() == 0) {
                leafToken = properties.getProperty("bamboo.leaf.client.leafToken");
            }
            // 判断服务token
            if (leafToken == null || (leafToken = leafToken.trim()).length() == 0) {
                throw new BambooLeafException("mode=Remote ,bamboo.leaf.client.leafToken is not null!");
            }
            clientConfig.setLeafToken(leafToken);

            int readTimeout = leafClientProperties.getReadTimeout();
            if (readTimeout == 0) {
                readTimeout = NumberUtils.toInt(properties.getProperty("bamboo.leaf.client.readTimeout"), 5000);
            }
            clientConfig.setReadTimeout(readTimeout);

            int connectTimeout = leafClientProperties.getConnectTimeout();
            if (connectTimeout == 0) {
                connectTimeout = NumberUtils.toInt(properties.getProperty("bamboo.leaf.client.connectTimeout"), 5000);
            }
            clientConfig.setConnectTimeout(connectTimeout);

            String[] leafServers = leafServer.split(",");
            List<String> segmentServerList = new ArrayList<>(leafServers.length);
            List<String> snowServerList = new ArrayList<>(leafServers.length);
            for (String server : leafServers) {
                // segment remote api url
                String segmentUrl = MessageFormat.format(segmentServerUrl, server, leafToken);
                segmentServerList.add(segmentUrl);
                // snowflake remote api url
                String snowUrl = MessageFormat.format(snowServerUrl, server, leafToken, PNetUtils.getLocalHost());
                snowServerList.add(snowUrl);
            }
            clientConfig.setSegmentServerList(segmentServerList);
            clientConfig.setSnowServerList(snowServerList);
        }
        return clientConfig;

    }

    @Bean
    public SegmentDAO segmentDAO() {
        SegmentDAO segmentDAO = new SegmentDAOImpl();
        return segmentDAO;
    }

    @Bean(name = "segmentService")
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

    @Bean(name = "bambooLeafClient")
    public BambooLeafClient bambooLeafClient() {
        BambooLeafClient bambooLeafClient = new BambooLeafClientImpl();
        return bambooLeafClient;
    }
}