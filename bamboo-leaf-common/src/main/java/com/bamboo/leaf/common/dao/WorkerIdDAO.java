package com.bamboo.leaf.common.dao;

import com.bamboo.leaf.common.util.PURL;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/29 下午11:32
 */
public interface WorkerIdDAO {
    /**
     * 通过nameSpace,hostIp获取workerId
     * @param namespace 命名空间
     * @param hostIp 主机IP
     * @param configURL 参数
     * @return
     */
    int getWorkerId(String namespace, String hostIp, PURL configURL);
}
