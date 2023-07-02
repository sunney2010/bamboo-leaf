package com.bamboo.leaf.client;

import com.bamboo.leaf.client.constant.ParamConstant;
import com.bamboo.leaf.client.service.BambooLeafSegmentClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class BambooLeafSegmentClientTest extends BaseJunitTest {
    @Autowired
    BambooLeafSegmentClient bambooLeafSegmentClient;

    @Test
    public void autoDateSegmentIdTest() {
        String sequence = bambooLeafSegmentClient.autoDateSegmentId("autoDateSegmentId");
        logger.info("sequence:{}", sequence);
    }

    @Test
    public void autoDateSegmentIdParamTest() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ParamConstant.PARAM_PREFIX, "1234567");
        paramMap.put(ParamConstant.PARAM_INFIX, "1234567");
        paramMap.put(ParamConstant.PARAM_SUFFIX, "1234567");
        String sequence = bambooLeafSegmentClient.autoDateSegmentId("autoDateSegmentIdParam", paramMap);
        logger.info("sequence:{}", sequence);
    }

}
