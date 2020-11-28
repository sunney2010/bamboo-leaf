package com.bamboo.leaf.server;

import com.bamboo.leaf.server.dao.entity.TokenDO;
import com.bamboo.leaf.server.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/11/28 下午2:21
 */

@SpringBootTest
public class TokenServiceTest {
    @Resource
    TokenService tokenService;

    @Test
    public void insertTokenTest() {
        TokenDO tokenDO = new TokenDO();
        tokenDO.setNamespace("Test");
        tokenDO.setToken(UUID.randomUUID().toString());
        tokenService.insertToken(tokenDO);
    }
}
