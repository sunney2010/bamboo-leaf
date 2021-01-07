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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @description: 远程服务模式的实现类
 * @Author: Zhuzhi
 * @Date: 2020/12/2 下午11:04
 */
@Component("remoteSegmentService")
public class RemoteSegmentServiceImpl implements SegmentService {

    private static final Logger logger = LoggerFactory.getLogger(RemoteSegmentServiceImpl.class);

    @Override
    public SegmentRange getNextSegmentRange(String namespace, long maxValue) {
        String url = chooseService(namespace, maxValue);
        if (logger.isInfoEnabled()) {
            logger.info("getNextSegmentRange url:{}", url);
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
     * 远程URL拼装
     * @param namespace namespace
     * @param maxValue 最大值
     * @return
     */
    private String chooseService(String namespace, long maxValue) {
        List<String> segmentServerList = ClientConfig.getInstance().getSegmentServerList();
        if (null == segmentServerList) {
            String leafServer = ClientConfig.getInstance().getLeafServer();
            // 判断服务地址
            if (leafServer == null || leafServer.trim().length() == 0) {
                throw new BambooLeafException("mode=Remote ,bamboo.leaf.client.leafServer is not null!");
            }
            String leafToken = ClientConfig.getInstance().getLeafToken();
            // 判断服务token
            if (leafToken == null || leafToken.trim().length() == 0) {
                throw new BambooLeafException("mode=Remote,bamboo.leaf.client.leafToken is not null!");
            }
            String[] leafServers = ClientConfig.getInstance().getLeafServer().split(",");
            segmentServerList = new ArrayList<String>(leafServers.length);
            for (String server : leafServers) {
                // segment remote api url
                String segmentUrl = MessageFormat.format(ClientConstant.segmentServerUrl, server, leafToken, maxValue);
                segmentServerList.add(segmentUrl);

            }
            ClientConfig.getInstance().setSegmentServerList(segmentServerList);
        }
        String url = "";
        if (segmentServerList != null && segmentServerList.size() == 1) {
            url = segmentServerList.get(0);
        } else if (segmentServerList != null && segmentServerList.size() > 1) {
            Random r = new Random();
            url = segmentServerList.get(r.nextInt(segmentServerList.size()));
        }
        url += namespace;
        return url;
    }
}
