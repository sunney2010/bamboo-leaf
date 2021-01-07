package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ModeEnum;
import com.bamboo.leaf.client.service.BambooLeafSnowflakeClient;
import com.bamboo.leaf.client.utils.SnowflakeIdUtils;
import com.bamboo.leaf.core.constant.LeafConstant;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.factory.AbstractWorkerIdGeneratorFactory;
import com.bamboo.leaf.core.generator.SnowflakeGenerator;
import com.bamboo.leaf.core.generator.impl.DefaultSnowflakeGenerator;
import com.bamboo.leaf.core.generator.impl.DefaultWorkerIdGenerator;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.core.util.PNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/25 下午6:32
 */
@Service
public class BambooLeafSnowflakeClientImpl extends AbstractWorkerIdGeneratorFactory implements BambooLeafSnowflakeClient, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(BambooLeafSnowflakeClientImpl.class);

    private final static int SNOWFLAKE_ID_LENGTH = 13;
    private final static int SNOWFLAKE_NAMESPACE_LENGTH = 4;
    private final static int SNOWFLAKE_RANDOM_LENGTH = 3;


    private ApplicationContext applicationContext;

    @Resource(name = "workerIdService")
    WorkerIdService localWorkerIdService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public long snowflakeId(String namespace) {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace is null");
        }
        String hospIp = PNetUtils.getLocalHost();
        // get workerId
        Integer workerId = this.getWorkerId(namespace, hospIp);
        SnowflakeGenerator SnowflakeGenerator = this.getSnowflakeGenerator(namespace, workerId);
        return SnowflakeGenerator.nextId();
    }

    @Override
    public String snowflakeId16(String namespace) {
        StringBuilder id = new StringBuilder(16);
        // ------------------------------------------------------
        String snowflakeId = Long.toString(snowflakeId(namespace), LeafConstant.RADIX);
        // 不足13位前面补"0"
        for (int i = 0; i < (SNOWFLAKE_ID_LENGTH - snowflakeId.length()); i++) {
            id.append('0');
        }
        id.append(snowflakeId);
        // --------------------------------------------------------
        String str = Long.toString(SnowflakeIdUtils.nextLong(0L, LeafConstant.MAX_RANDOM), LeafConstant.RADIX);
        for (int i = 0; i < (SNOWFLAKE_RANDOM_LENGTH - str.length()); i++) {
            id.append('0');
        }
        id.append(str);
        return id.toString();
    }

    @Override
    public String snowflakeId20(String namespace) {

        if (namespace == null || (namespace = namespace.trim()).length() == 0) {
            throw new IllegalArgumentException("namespace required.");
        }
        StringBuilder id = new StringBuilder(20);
        String snowflakeId = Long.toString(snowflakeId(namespace), LeafConstant.RADIX);
        // 不足13位前面补"0"
        for (int i = 0; i < (SNOWFLAKE_ID_LENGTH - snowflakeId.length()); i++) {
            id.append('0');
        }
        id.append(snowflakeId);
        //
        String namespaceId = Long.toString(getNamespaceId(namespace), LeafConstant.RADIX);
        for (int i = 0; i < (SNOWFLAKE_NAMESPACE_LENGTH - namespaceId.length()); i++) {
            id.append('0');
        }
        id.append(namespaceId);
        // --------------------------------------------------------
        String str = Long.toString(SnowflakeIdUtils.nextLong(0L, LeafConstant.MAX_RANDOM), LeafConstant.RADIX);
        for (int i = 0; i < (SNOWFLAKE_RANDOM_LENGTH - str.length()); i++) {
            id.append('0');
        }
        id.append(str);
        return id.toString();
    }

    /**
     * 获取namespace的随机数
     *
     * @param namespace namespace
     * @return
     */
    private static Long getNamespaceId(String namespace) {
        if (namespace == null || (namespace = namespace.trim()).length() == 0) {
            throw new IllegalArgumentException("namespace requried.");
        }
        int[] ints = SnowflakeIdUtils.toCodePoints(namespace);
        int sums = 0;
        for (int i : ints) {
            sums += i;
        }
        return (long) (sums % LeafConstant.MAX_NAMESPACE);
    }

    @Override
    public String parsSnowflakeId(String namespace, long snowflakeId) {
        if (namespace == null) {
            throw new IllegalArgumentException("namespace is null");
        }
        String hospIp = PNetUtils.getLocalHost();
        // get workerId
        Integer workerId = this.getWorkerId(namespace, hospIp);
        SnowflakeGenerator SnowflakeGenerator = this.getSnowflakeGenerator(namespace, workerId);

        return SnowflakeGenerator.parseSnowId(snowflakeId);
    }

    @Override
    protected SnowflakeGenerator createSnowflakeGenerator(int workerId) {
        return new DefaultSnowflakeGenerator(workerId);
    }

    @Override
    protected Integer createWorkerId(String namespace, String hostIp) {
        Integer workerId = null;
        //获取当前的配置的模式
        String mode = ClientConfig.getInstance().getMode();
        if (null == mode || mode.trim().length() == 0) {
            logger.error("bamboo.leaf.client.mode is not null");
            throw new BambooLeafException("bamboo.leaf.client.mode is not null");
        }
        //判断配置的模式
        if (mode.equalsIgnoreCase(ModeEnum.Remote.name())) {
            workerId = new RemoteWorkerIdServiceImpl().getWorkerId(namespace, hostIp);
        } else if (mode.equalsIgnoreCase(ModeEnum.Local.name())) {
            workerId = new DefaultWorkerIdGenerator(localWorkerIdService, namespace, hostIp).getWorkerId();
        }
        return workerId;
    }
}
