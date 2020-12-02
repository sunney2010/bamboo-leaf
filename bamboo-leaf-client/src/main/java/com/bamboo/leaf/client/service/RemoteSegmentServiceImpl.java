package com.bamboo.leaf.client.service;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.utils.HttpUtils;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


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
        String response = HttpUtils.post(url, ClientConfig.getInstance().getReadTimeout(),
                ClientConfig.getInstance().getConnectTimeout());
        logger.info("bamboo client getNextSegmentId end, response:" + response);
        if (response == null || "".equals(response.trim())) {
            return null;
        }
        SegmentRange segment = new SegmentRange(1,100);
        String[] arr = response.split(",");
        segment.setCurrentVal(new AtomicLong(Long.parseLong(arr[0])));
        segment.setLoadingVal(Long.parseLong(arr[1]));
//        segment.setMaxId(Long.parseLong(arr[2]));
        segment.setDelta(Integer.parseInt(arr[3]));
        segment.setRemainder(Integer.parseInt(arr[4]));
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
