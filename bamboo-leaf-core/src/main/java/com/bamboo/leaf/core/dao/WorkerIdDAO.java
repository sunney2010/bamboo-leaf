package com.bamboo.leaf.core.dao;

/**
 * @description: WorkerId DAO接口
 * @Author: Zhuzhi
 * @Date: 2020/11/29 下午11:32
 */
public interface WorkerIdDAO {
    /**
     * 通过nameSpace,hostIp获取workerId
     * @param namespace 命名空间
     * @param hostIp 主机IP
     * @return
     */
    int queryWorkerId(String namespace, String hostIp);
    /**
     * 通过nameSpace,hostIp获取workerId
     * @param namespace 命名空间
     * @param hostIp 主机IP
     * @param workerId 值
     * @return
     */
    int insertWorkerId(String namespace, String hostIp,int workerId);
    /**
     * 通过nameSpace,hostIp获取workerId
     * @param namespace 命名空间
     * @param hostIp 主机IP
     * @return
     */
    int queryMaxWorkerId(String namespace, String hostIp);
}
