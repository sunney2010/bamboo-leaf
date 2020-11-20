package com.bamboo.leaf.common.entity;

/**
 * @author zhuzhi
 * @date 2020/11/19
 */
public class Result {

    private int code;
    private ResultEnum resultEnum;

    public Result(int code, ResultEnum resultEnum) {
        this.code = code;
        this.resultEnum = resultEnum;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the resultEnum
     */
    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    /**
     * @param resultEnum
     *            the resultEnum to set
     */
    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

}
