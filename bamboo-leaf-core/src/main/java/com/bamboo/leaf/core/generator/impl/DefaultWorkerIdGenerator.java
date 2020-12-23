package com.bamboo.leaf.core.generator.impl;

import com.bamboo.leaf.core.generator.WorkerIdGenerator;
import com.bamboo.leaf.core.service.WorkerIdService;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/23 下午9:35
 */
public class DefaultWorkerIdGenerator implements WorkerIdGenerator {

    WorkerIdService workerIdService;
    String namespace;
    String hostIp;

    public DefaultWorkerIdGenerator(WorkerIdService workerIdService, String namespace, String hostIp) {
        this.workerIdService = workerIdService;
        this.namespace = namespace;
        this.hostIp = hostIp;
    }

    @Override
    public int getWorkerId() {

        return workerIdService.getWorkerId(namespace, hostIp);
    }
}
