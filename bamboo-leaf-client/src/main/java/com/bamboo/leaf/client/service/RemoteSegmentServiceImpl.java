package com.bamboo.leaf.client.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.utils.HttpUtils;
import com.bamboo.leaf.core.common.ResultCode;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;


/**
 * @description: 远程服务模式的实现类
 * @Author: Zhuzhi
 * @Date: 2020/12/2 下午11:04
 */
public class RemoteSegmentServiceImpl implements SegmentService {

    private static final Logger logger = LoggerFactory.getLogger(RemoteSegmentServiceImpl.class);

    @Override
    public SegmentRange getNextSegmentRange(String namespace) {
        String url = chooseService(namespace);
        if(logger.isInfoEnabled()){
            logger.info("getNextSegmentRange url:{}",url);
        }
        String response = HttpUtils.post(url, ClientConfig.getInstance().getReadTimeout(),
                ClientConfig.getInstance().getConnectTimeout());
        logger.info("bamboo client getNextSegmentId end, response:" + response);
        if (response == null || "".equals(response.trim())) {
            return null;
        }
        Type type = new TypeReference<ResultResponse<SegmentRange>>() {}.getType();
        ResultResponse<SegmentRange> resultDto = JSON.parseObject(response, type);
        String result = resultDto.getResult();
        SegmentRange segment = null;
        if ((ResultCode.SUCCESS.name()).equalsIgnoreCase(result)) {
            segment = resultDto.getResultData();
        }
        return segment;
    }

    private String chooseService(String namespace) {
        List<String> serverList = ClientConfig.getInstance().getServerList();
        String url = "";
        if (serverList != null && serverList.size() == 1) {
            url = serverList.get(0);
        } else if (serverList != null && serverList.size() > 1) {
            Random r = new Random();
            url = serverList.get(r.nextInt(serverList.size()));
        }
        url += namespace;
        return url;
    }
}
