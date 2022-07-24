package com.bamboo.leaf.server;

import com.bamboo.leaf.server.dao.entity.TokenDO;
import com.bamboo.leaf.server.service.TokenService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/28 下午2:21
 */

public class TokenServiceTest extends BaseJunit {

    @Resource
    TokenService tokenService;

    @Test
    public void insertTokenTest() {
        logger.info("=======insert token start=====");
        TokenDO tokenDO = new TokenDO();
        tokenDO.setAppId("Test");
        tokenDO.setToken(UUID.randomUUID().toString());
        tokenService.insertToken(tokenDO);
        logger.info("=======insert token end=====");
    }
}
