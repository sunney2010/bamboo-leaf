package com.bamboo.leaf.core.service.impl;

import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.service.WorkerIdService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/4 上午11:20
 */
@Component
public class WorkerIdServiceImpl implements WorkerIdService {
    @Resource
    WorkerIdDAO workerIdDAO;

    @Override
    public int getWorkerId(String namespace, String hostIp) {
        return workerIdDAO.getWorkerId(namespace,hostIp);
    }
}
