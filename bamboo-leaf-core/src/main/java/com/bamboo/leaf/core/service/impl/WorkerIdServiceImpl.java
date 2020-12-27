package com.bamboo.leaf.core.service.impl;

import com.bamboo.leaf.core.constant.LeafConstant;
import com.bamboo.leaf.core.dao.WorkerIdDAO;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.service.WorkerIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/4 上午11:20
 */
@Component
public class WorkerIdServiceImpl implements WorkerIdService {

    private static final Logger logger = LoggerFactory.getLogger(WorkerIdServiceImpl.class);

    @Resource
    WorkerIdDAO workerIdDAO;

    @Override
    public synchronized int getWorkerId(String namespace, String hostIp) {
        int workerId = 0;
        try {
            workerId = workerIdDAO.queryWorkerId(namespace, hostIp);
            if (workerId <= 0) {
                workerId = workerIdDAO.queryMaxWorkerId(namespace, hostIp);
                //在前最大的workerId+1
                workerId++;
                workerIdDAO.insertWorkerId(namespace, hostIp, workerId);
            }
            if (workerId < LeafConstant.INIT_WORKER_ID || workerId > LeafConstant.MAX_WORKER_ID) {
                logger.error(" workerId is scope [{}-{}],workerId:{}", LeafConstant.INIT_WORKER_ID,
                        LeafConstant.MAX_WORKER_ID, workerId);
                throw new BambooLeafException(
                        "workerId is scope [" + LeafConstant.INIT_WORKER_ID + "-" + LeafConstant.MAX_WORKER_ID + "]");
            }
        } catch (Exception e) {
            logger.error("getWorkerId is error,msg:", e);
            throw new BambooLeafException(e);
        }
        return workerId;
    }
}
