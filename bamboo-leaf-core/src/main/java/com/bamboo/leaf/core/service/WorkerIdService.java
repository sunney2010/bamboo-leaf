package com.bamboo.leaf.core.service;

import com.bamboo.leaf.core.util.PURL;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/4 上午11:19
 */
public interface WorkerIdService {
    /**
     * 通过nameSpace,hostIp获取workerId
     * @param namespace 命名空间
     * @param hostIp 主机IP
     * @return
     */
    int getWorkerId(String namespace, String hostIp);
}
