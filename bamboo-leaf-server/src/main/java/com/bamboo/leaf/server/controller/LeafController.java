package com.bamboo.leaf.server.controller;

import com.bamboo.leaf.server.dao.entity.TokenDO;
import com.bamboo.leaf.server.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichunming
 * @date 2020/11/19
 */
@RestController
@RequestMapping("/leaf/")
public class LeafController {
    private static final Logger logger = LoggerFactory.getLogger(LeafController.class);

    @Autowired
    TokenService tokenService;

    @RequestMapping("/insertToken")
    public int insertToken(TokenDO tokenDO) {
        return tokenService.insertToken(tokenDO);
    }

}
