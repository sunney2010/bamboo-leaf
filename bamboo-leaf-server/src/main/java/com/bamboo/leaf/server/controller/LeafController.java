package com.bamboo.leaf.server.controller;

import com.bamboo.leaf.core.common.ResultCode;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.service.WorkerIdService;
import com.bamboo.leaf.server.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bamboo服务Controller层
 *
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

    @RequestMapping("/hello")
    public String hello() {
        //格式化
        DateTimeFormatter fmTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        String message = "hello success! current Time:" + now.format(fmTime);
        logger.info(message);
        return message;
    }

    @RequestMapping("/segment/nextSegmentRange")
    public ResultResponse<SegmentRange> nextSegmentRange(String namespace, Long maxValue, Integer step, String token) {
        ResultResponse<SegmentRange> response = new ResultResponse<>();
        logger.info("nextSegmentRange parameter, namespace:{},maxValue:{},step:{},token:{},", namespace, maxValue, step, token);
        if (!tokenService.canVisit(namespace, token)) {
            response.setResult(ResultCode.FAIL.getMessage());
            response.setErrMsg(ResultCode.TOKEN_ERR.getMessage());
            return response;
        }
        try {
            SegmentRange segment = segmentService.getNextSegmentRange(namespace, maxValue, step);
            response.setResultData(segment);
            logger.info("nextSegmentRange is success,namespace:{},leafVal:{}", namespace, segment.getCurrentVal());
        } catch (Exception e) {
            response.setResult(ResultCode.FAIL.getMessage());
            response.setErrMsg(ResultCode.SYS_ERR.getMessage());
            logger.error("nextSegmentRange error", e);
        }
        return response;
    }

    @RequestMapping("/snowflake/queryWorkerId")
    public ResultResponse<Integer> queryWorkerId(String namespace,String appId, String hostIp, String token) {
        logger.info("queryWorkerId parameter: namespace:{},appId:{},hostIp:{},token:{}", namespace,appId, hostIp, token);
        ResultResponse<Integer> response = new ResultResponse<>();
        if (StringUtils.isEmpty(namespace)||StringUtils.isEmpty(appId) || StringUtils.isEmpty(hostIp) || StringUtils.isEmpty(token)) {
            response.setResult(ResultCode.FAIL.getMessage());
            response.setErrMsg(ResultCode.PARA_ERR.getMessage());
            return response;
        }
        if (!tokenService.canVisit(appId, token)) {
            logger.warn("Access Denied,appId:{},token:{}", appId, token);
            response.setResult(ResultCode.FAIL.getMessage());
            response.setErrMsg(ResultCode.TOKEN_ERR.getMessage());
            return response;
        }
        try {
            int workerId = workerIdService.getWorkerId(namespace, hostIp);
            response.setResultData(workerId);
            logger.info("queryWorkerId success,appId:{},hostIp:{},workerId:{}", appId, hostIp, workerId);
        } catch (Exception e) {
            response.setResult(ResultCode.FAIL.getMessage());
            response.setErrMsg(ResultCode.SYS_ERR.getMessage());
            logger.error("queryWorkerId is error", e);
        }
        return response;
    }
}
