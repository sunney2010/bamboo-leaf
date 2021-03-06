package com.bamboo.leaf.client.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.constant.ClientConstant;
import com.bamboo.leaf.client.utils.HttpUtils;
import com.bamboo.leaf.core.common.ResultCode;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.exception.BambooLeafException;
import com.bamboo.leaf.core.service.SegmentService;
import com.bamboo.leaf.core.util.PURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 * @description: 远程服务模式的实现类
 * @Author: Zhuzhi
 * @Date: 2020/12/2 下午11:04
 */
@Component("remoteSegmentService")
public class RemoteSegmentServiceImpl implements SegmentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public SegmentRange getNextSegmentRange(String namespace, long maxValue, Integer step) {
        String url = chooseService(namespace, maxValue, step);
        if (logger.isInfoEnabled()) {
            logger.info(" request nextSegmentRange URL:{}", url);
        }
        String response = HttpUtils.post(url, ClientConfig.getInstance().getReadTimeout(),
                ClientConfig.getInstance().getConnectTimeout());
        logger.info("bamboo client getNextSegmentId end, response:" + response);
        if (response == null || "".equals(response.trim())) {
            return null;
        }
        Type type = new TypeReference<ResultResponse<SegmentRange>>() {
        }.getType();
        ResultResponse<SegmentRange> resultDto = JSON.parseObject(response, type);
        String result = resultDto.getResult();
        SegmentRange segment = null;
        if ((ResultCode.SUCCESS.name()).equalsIgnoreCase(result)) {
            segment = resultDto.getResultData();
        } else {
            logger.error("request remote is error,msg:{}", resultDto.getErrMsg());
        }
        return segment;
    }

    /**
     * 远程URL拼装,因为step为动态，每次请求重新并装URL
     *
     * @param namespace namespace
     * @param maxValue  最大值
     * @param step      动态步长
     * @return
     */
    private String chooseService(String namespace, long maxValue, Integer step) {

        String leafServer = ClientConfig.getInstance().getLeafServer();
        // 判断服务地址
        if (leafServer == null || leafServer.trim().length() == 0) {
            throw new BambooLeafException("mode=Remote ,bamboo.leaf.client.leafServer is not null!");
        }
        String leafPort = ClientConfig.getInstance().getLeafPort();
        // 判断服务地址
        if (leafPort == null || leafPort.trim().length() == 0) {
            throw new BambooLeafException("mode=Remote ,bamboo.leaf.client.leafPort is not null!");
        }
        String leafToken = ClientConfig.getInstance().getLeafToken();
        // 判断服务token
        if (leafToken == null || leafToken.trim().length() == 0) {
            throw new BambooLeafException("mode=Remote,bamboo.leaf.client.leafToken is not null!");
        }
        Map<String, String> parameters = new HashMap<String, String>(4);
        parameters.put(ClientConstant.LEAF_TOKEN, leafToken);
        parameters.put(ClientConstant.LEAF_MAXVALUE, (maxValue + ""));
        parameters.put(ClientConstant.LEAF_STEP, (step + ""));
        parameters.put(ClientConstant.LEAF_NAMESPACE, namespace);

        PURL purl = new PURL("http", leafServer, Integer.parseInt(leafPort), ClientConstant.LEAF_SEGMENT_PATH, parameters);
        String segmentUrl = purl.toFullString();
        return segmentUrl;
    }
}
