package com.bamboo.leaf.core.service;


/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/4 上午11:19
 */
public interface WorkerIdService {
    /**
     * 通过 namespace,hostIp获取workerId
     * @param namespace namespace
     * @param hostIp 主机IP
     * @return
     */
    int getWorkerId(String namespace, String hostIp);
}
