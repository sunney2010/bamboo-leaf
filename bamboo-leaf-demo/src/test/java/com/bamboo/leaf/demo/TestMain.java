package com.bamboo.leaf.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bamboo.leaf.core.common.ResultResponse;
import com.bamboo.leaf.core.entity.DemoDO;
import com.bamboo.leaf.core.entity.SegmentRange;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhuzhi
 * @date 2020/12/09
 */
public class TestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        segment();
        demo();

    }

    public static void segment() {

        ResultResponse<SegmentRange> resultDto = new ResultResponse<>();
        SegmentRange segment = new SegmentRange();
        segment.setRemainder(1);
        segment.setDelta(1);
        segment.setCurrentVal(new AtomicLong(1));
        segment.setLoadingVal(100);
        segment.setMaxId(100L);
        resultDto.setResultData(segment);

        String str = JSON.toJSONString(resultDto);
        System.out.println(str);
        Type type = new TypeReference<ResultResponse<SegmentRange>>(SegmentRange.class) {
        }.getType();
        ResultResponse<SegmentRange> resultDto1 = JSON.parseObject(str, type);
        String data = resultDto1.getResult();

        SegmentRange segment1 = resultDto1.getResultData();

        System.out.println(data);
        System.out.println(JSON.toJSONString(segment1));

        String json = "{\"resultData\":{\"min\":20980,\"max\":21980,\"currentVal\":20980,\"over\":false,\"loadingVal\":21230,\"delta\":1,\"remainder\":1,\"init\":false},\"result\":\"success\",\"errMsg\":null}";

        ResultResponse<SegmentRange> resultDto2 = JSON.parseObject(json, type);
        SegmentRange segment2 = resultDto2.getResultData();
        String data1 = resultDto2.getResult();
        System.out.println(data1);
        System.out.println(JSON.toJSONString(segment2));

    }

    public static void demo() {

        ResultResponse<DemoDO> demoDto = new ResultResponse<>();
        demoDto.setResult("success");
        DemoDO demoDO = new DemoDO();
        demoDO.setAge(99);
        demoDO.setId(88);
        demoDO.setName("zhuzhi");
        demoDO.setNum(100L);
        demoDO.setDelta(100);
        demoDO.setRemainder(100);
        demoDto.setResultData(demoDO);
        String demoStr = JSON.toJSONString(demoDto);
        Type type = new TypeReference<ResultResponse<DemoDO>>(DemoDO.class) {
        }.getType();
        ResultResponse<DemoDO> demoDto1 = JSON.parseObject(demoStr, type);
        String su = demoDto1.getResult();
        DemoDO de = demoDto1.getResultData();
        System.out.println(su);

    }

}
