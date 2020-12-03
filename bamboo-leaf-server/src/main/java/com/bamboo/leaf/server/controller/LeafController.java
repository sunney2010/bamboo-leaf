package com.bamboo.leaf.server.controller;

import com.bamboo.leaf.core.common.ErrorCode;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.common.ResultResponse;
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
    @Resource
    SegmentService segmentService;

    @RequestMapping("/insertToken")
    public int insertToken(TokenDO tokenDO) {
        return tokenService.insertToken(tokenDO);
    }

    @RequestMapping("/hello")
    public String hello() {
        logger.info("hello success");
        return "success";
    }
    @RequestMapping("nextSegmentRange")
    public ResultResponse<SegmentRange> nextSegmentRange(String namespace, String token) {
        ResultResponse<SegmentRange> response = new ResultResponse<>();
        if (!tokenService.canVisit(namespace, token)) {
            response.setCode(ErrorCode.TOKEN_ERR.getCode());
            response.setMessage(ErrorCode.TOKEN_ERR.getMessage());
            return response;
        }
        try {
            SegmentRange segment = segmentService.getNextSegmentRange(namespace);
            response.setData(segment);
        } catch (Exception e) {
            response.setCode(ErrorCode.SYS_ERR.getCode());
            response.setMessage(e.getMessage());
            logger.error("nextSegmentRange error", e);
        }
        return response;
    }

}
