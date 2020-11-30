package com.bamboo.leaf.client.service;

import com.bamboo.leaf.client.config.ClientConfig;
import com.bamboo.leaf.client.utils.HttpUtils;
import com.bamboo.leaf.core.entity.SegmentRange;
import com.bamboo.leaf.core.service.SegmentRangeService;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * @author du_imba
 */
public class HttpSegmentRangeServiceImpl implements SegmentRangeService {

    private static final Logger logger = Logger.getLogger(HttpSegmentRangeServiceImpl.class.getName());

    @Override
    public SegmentRange getNextSegmentId(String namespace) {
        String url = chooseService(namespace);
        String response = HttpUtils.post(url, ClientConfig.getInstance().getReadTimeout(),
                ClientConfig.getInstance().getConnectTimeout());
        logger.info("tinyId client getNextSegmentId end, response:" + response);
        if (response == null || "".equals(response.trim())) {
            return null;
        }
        SegmentRange segment = new SegmentRange();
        String[] arr = response.split(",");
        segment.setCurrentId(new AtomicLong(Long.parseLong(arr[0])));
        segment.setLoadingId(Long.parseLong(arr[1]));
        segment.setMaxId(Long.parseLong(arr[2]));
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
