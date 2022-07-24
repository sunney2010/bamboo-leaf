package com.bamboo.leaf.core.service;


/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/4 上午11:19
 */
public interface WorkerIdService {
    /**
     * 通过 appId,hostIp获取workerId
     * @param appId 应用编号
     * @param hostIp 主机IP
     * @return
     */
    int getWorkerId(String appId, String hostIp);
}
