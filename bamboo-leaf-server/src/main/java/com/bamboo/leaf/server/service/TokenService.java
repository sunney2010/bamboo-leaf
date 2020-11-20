 package com.bamboo.leaf.server.service;

 /**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface TokenService {
    /**
     * 是否有权限
     * @param namespace
     * @param token
     * @return
     */
    boolean canVisit(String namespace, String token);

}
