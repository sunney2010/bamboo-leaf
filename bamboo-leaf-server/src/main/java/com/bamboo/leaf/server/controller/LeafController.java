package com.bamboo.leaf.server.controller;

import com.bamboo.leaf.server.dao.entity.TokenDO;
import com.bamboo.leaf.server.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lichunming
 * @date 2020/11/19
 */
@RestController
@RequestMapping("/leaf/")
public class LeafController {
    private static final Logger logger = LoggerFactory.getLogger(LeafController.class);

    @Resource
    TokenService tokenService;

    @RequestMapping("/insertToken")
    public int insertToken(TokenDO tokenDO) {
        return tokenService.insertToken(tokenDO);
    }

    @RequestMapping("/hello")
    public String hello() {
        logger.info("hello success");
        return "success";
    }

}
