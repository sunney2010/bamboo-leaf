package com.bamboo.leaf.client.dto;

import com.bamboo.leaf.core.entity.SegmentRange;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/8 下午12:44
 */
public class ResultDto {

    private SegmentRange data;
    private String result;
    private String errMsg;

    public SegmentRange getData() {
        return data;
    }

    public void setData(SegmentRange data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
