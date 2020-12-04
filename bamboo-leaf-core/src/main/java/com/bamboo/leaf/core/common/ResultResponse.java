package com.bamboo.leaf.core.common;

/**
 * @description: TODO
 * @Author: Zhuzhi
 * @Date: 2020/12/3 下午7:50
 */
public class ResultResponse<T> {
    private T data;
    private String result = "success";
    private String errMsg ;

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
