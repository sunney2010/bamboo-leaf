package com.bamboo.leaf.client.service.impl;

import com.bamboo.leaf.core.service.WorkerIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/23 下午10:09
 */
@Component("localWorkerIdService")
public class LocalWorkerIdServiceImpl implements WorkerIdService {
    private static final Logger logger = LoggerFactory.getLogger(LocalWorkerIdServiceImpl.class);

    WorkerIdService workerIdService;
    @Override
    public int getWorkerId(String namespace, String hostIp) {
        return 0;
    }
}
