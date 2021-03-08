package com.bamboo.leaf.core.entity;

/**
 * Segment返回结果
 *
 * @author zhuzhi
 * @date 2020/11/19
 */
public class Result {

    /**
     * 当前值
     */
    private long val;

    /**
     * 动态步长
     */
    private int nextStep;
    /**
     * 结果
     */
    private ResultEnum resultEnum;


    public Result(long val, int nextStep, ResultEnum resultEnum) {
        this.val = val;
        this.nextStep = nextStep;
        this.resultEnum = resultEnum;
    }

    public Result(long val, ResultEnum resultEnum) {
        this.val = val;
        this.resultEnum = resultEnum;
    }

    /**
     * @return the val
     */
    public long getVal() {
        return val;
    }


    /**
     * @param val the val to set
     */
    public void setVal(long val) {
        this.val = val;
    }


    /**
     * @return the resultEnum
     */
    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    /**
     * @param resultEnum the resultEnum to set
     */
    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public int getNextStep() {
        return nextStep;
    }

    public void setNextStep(int nextStep) {
        this.nextStep = nextStep;
    }
}
