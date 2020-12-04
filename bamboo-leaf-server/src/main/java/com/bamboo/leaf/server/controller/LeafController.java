package com.bamboo.leaf.server.controller;

import com.bamboo.leaf.core.common.ErrorCode;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.server.dao.entity.TokenDO;
import com.bamboo.leaf.server.service.TokenService;
import org.apache.commons.lang3.StringUtils;
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
public class LeafController {

    private static final Logger logger = LoggerFactory.getLogger(LeafController.class);

    @Resource
    TokenService tokenService;
    @Resource
    SegmentService segmentService;
    @Resource
    WorkerIdService workerIdService;

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
        logger.info("nextSegmentRange parameter: namespace:{},token:{}",namespace,token);
        if (!tokenService.canVisit(namespace, token)) {
            response.setResult(ErrorCode.FAIL.getMessage());
            response.setErrMsg(ErrorCode.TOKEN_ERR.getMessage());
            return response;
        }
        try {
            SegmentRange segment = segmentService.getNextSegmentRange(namespace);
            response.setData(segment);
            logger.info("nextSegmentRange is success,namespace:{},leafVal:{}", namespace, segment.getCurrentVal());
        } catch (Exception e) {
            response.setResult(ErrorCode.FAIL.getMessage());
            response.setErrMsg(ErrorCode.SYS_ERR.getMessage());
            logger.error("nextSegmentRange error", e);
        }
        return response;
    }

    @RequestMapping("/queryWorkerId")
    public ResultResponse<Integer> queryWorkerId(String namespace, String hostIp, String token) {
        logger.info("queryWorkerId parameter: namespace:{},hostIp:{},token:{}", namespace, hostIp, token);
        ResultResponse<Integer> response = new ResultResponse<>();
        if (StringUtils.isEmpty(namespace) || StringUtils.isEmpty(hostIp) || StringUtils.isEmpty(token)) {
            response.setResult(ErrorCode.FAIL.getMessage());
            response.setErrMsg(ErrorCode.PARA_ERR.getMessage());
            return response;
        }
        if (!tokenService.canVisit(namespace, token)) {
            response.setResult(ErrorCode.FAIL.getMessage());
            response.setErrMsg(ErrorCode.TOKEN_ERR.getMessage());
            return response;
        }
        try {
            int workerId = workerIdService.getWorkerId(namespace, hostIp);
            response.setData(workerId);
            logger.info("queryWorkerId success,namespace:{},hostIp:{},workerId:{}", namespace, hostIp, workerId);
        } catch (Exception e) {
            response.setResult(ErrorCode.FAIL.getMessage());
            response.setErrMsg(ErrorCode.SYS_ERR.getMessage());
            logger.error("queryWorkerId is error", e);
        }
        return response;
    }
}
