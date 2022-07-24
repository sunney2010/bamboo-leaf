 package com.bamboo.leaf.server.service;

 import com.bamboo.leaf.server.dao.entity.TokenDO;

 /**
 * @author zhuzhi
 * @date 2020/11/19
 */
public interface TokenService {
    /**
     * 是否有权限
     * @param appId 应用编号
     * @param token token
     * @return
     */
    boolean canVisit(String appId, String token);

     /**
      * 新增
      * @param tokenDO
      * @return
      */
    int insertToken(TokenDO tokenDO);

}
