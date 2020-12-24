package com.bamboo.leaf.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.utils.HttpUtils;
import com.bamboo.leaf.client.utils.SnowflakeIdUtils;
import com.bamboo.leaf.core.common.ResultCode;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.constant.LeafConstant;
import com.bamboo.leaf.core.service.WorkerIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

/**
 * @description: TODO
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
            workerId = (int) SnowflakeIdUtils.nextLong(LeafConstant.INIT_WORKERID, LeafConstant.MAX_WORKERID);
            logger.error("get workerId is error response is null,random workerId:{}", workerId);
        } else {
            Type type = new TypeReference<ResultResponse<Integer>>() {
            }.getType();
            ResultResponse<Integer> resultDto = JSON.parseObject(response, type);
            String result = resultDto.getResult();
            if ((ResultCode.SUCCESS.name()).equalsIgnoreCase(result)) {
                workerId = resultDto.getResultData();
            } else {
                // 如果获取失败，则使用随机数备用
                workerId = (int) SnowflakeIdUtils.nextLong(LeafConstant.INIT_WORKERID, LeafConstant.MAX_WORKERID);
                logger.error("get workerId is error response is :{},random workerId:{}", result, workerId);
            }
        }
        return workerId;
    }

    private String chooseService(String namespace) {
        List<String> serverList = ClientConfig.getInstance().getSnowServerList();
        String url = "";
        if (serverList != null && serverList.size() == 1) {
            url = serverList.get(0);
        } else if (serverList != null && serverList.size() > 1) {
            Random r = new Random();
            url = serverList.get(r.nextInt(serverList.size()));
        }
        url += namespace;
        return url;
    }
}
