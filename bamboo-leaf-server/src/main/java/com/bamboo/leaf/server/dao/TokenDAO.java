package com.bamboo.leaf.server.dao;

import com.bamboo.leaf.server.dao.entity.TokenDO;

import java.util.List;

/**
 * @author du_imba
 */
public interface TokenDAO {
    /**
     * 查询db中所有的token信息
     *
     * @return
     */
    List<TokenDO> selectAll();

    /**
     * 查询db中所有的token信息
     *
     * @param tokenDO
     * @return
     */
    int insertToken(TokenDO tokenDO);

    /**
     * 查询Token信息
     *
     * @param appId appId
     * @return
     */
    TokenDO queryTokenByAppId(String appId);

}
