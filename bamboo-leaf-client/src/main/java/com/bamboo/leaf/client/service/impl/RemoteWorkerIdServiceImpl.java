package com.bamboo.leaf.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ClientConstant;
import com.bamboo.leaf.client.utils.HttpUtils;
import com.bamboo.leaf.client.utils.SnowflakeIdUtils;
import com.bamboo.leaf.core.common.ResultCode;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.constant.LeafConstant;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.core.util.PNetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description: WorkerId远程服务类
 * @Author: Zhuzhi
 * @Date: 2020/12/23 下午12:58
 */
@Component("remoteWorkerIdService")
public class RemoteWorkerIdServiceImpl implements WorkerIdService {
    private static final Logger logger = LoggerFactory.getLogger(RemoteWorkerIdServiceImpl.class);
    @Override
    public int getWorkerId(String namespace, String hostIp) {
        int workerId = 0;
        String url = chooseService(namespace);
        if (logger.isInfoEnabled()) {
            logger.info("getWorkerId url:{}", url);
        }
        String response = HttpUtils.post(url, ClientConfig.getInstance().getReadTimeout(),
                ClientConfig.getInstance().getConnectTimeout());
        logger.info("bamboo client getWorkerId end, response:" + response);
        if (response == null || "".equals(response.trim())) {
            // 如果获取失败，则使用随机数备用
            workerId = (int) SnowflakeIdUtils.nextLong(LeafConstant.INIT_WORKER_ID, LeafConstant.MAX_WORKER_ID);
            logger.error("get workerId is error response is null,random workerId:{}", workerId);
        } else {
            Type type = new TypeReference<ResultResponse<Integer>>() {
            }.getType();
            ResultResponse<Integer> resultDto = JSON.parseObject(response, type);
            String result = resultDto.getResult();
            if ((ResultCode.SUCCESS.name()).equalsIgnoreCase(result)) {
                workerId = resultDto.getResultData();
            } else {
                logger.error("request remote is error,msg:{}", resultDto.getErrMsg());
                // 如果获取失败，则使用随机数备用
                workerId = (int) SnowflakeIdUtils.nextLong(LeafConstant.INIT_WORKER_ID, LeafConstant.MAX_WORKER_ID);
                logger.error("get workerId is error response is :{},random workerId:{}", result, workerId);
            }
        }
        return workerId;
    }

    private String chooseService(String namespace) {
        List<String> snowServerList = ClientConfig.getInstance().getSnowServerList();
        if (null == snowServerList) {
            String leafServer = ClientConfig.getInstance().getLeafServer();
            // 判断服务地址
            if (leafServer == null || leafServer.trim().length() == 0) {
                throw new BambooLeafException("mode=Remote ,bamboo.leaf.client.leafServer is not null!");
            }
            String leafToken = ClientConfig.getInstance().getLeafToken();
            // 判断服务token
            if (leafToken == null || leafToken.trim().length() == 0) {
                throw new BambooLeafException("mode=Remote ,bamboo.leaf.client.leafToken is not null!");
            }

            String[] leafServers = ClientConfig.getInstance().getLeafServer().split(",");
            snowServerList = new ArrayList<String>(leafServers.length);
            for (String server : leafServers) {
                // snowUrl remote api url
                String snowUrl = MessageFormat.format(ClientConstant.snowflakeServerUrl, server, leafToken, PNetUtils.getLocalHost());
                snowServerList.add(snowUrl);

            }
            ClientConfig.getInstance().setSnowServerList(snowServerList);
        }
        String url = "";
        if (snowServerList != null && snowServerList.size() == 1) {
            url = snowServerList.get(0);
        } else if (snowServerList != null && snowServerList.size() > 1) {
            Random r = new Random();
            url = snowServerList.get(r.nextInt(snowServerList.size()));
        }
        url += namespace;
        return url;
    }
}
