package com.bamboo.leaf.core.common;

/**
 * @description: 返回结果类
 * @Author: Zhuzhi
 * @Date: 2020/12/3 下午7:50
 */
public class ResultResponse<T> {

    private String result = "success";
    private String errMsg ;
    private T resultData;

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
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
